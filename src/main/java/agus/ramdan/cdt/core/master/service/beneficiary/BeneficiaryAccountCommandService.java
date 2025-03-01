package agus.ramdan.cdt.core.master.service.beneficiary;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.repository.BeneficiaryAccountRepository;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import agus.ramdan.cdt.core.master.service.bank.BankQueryService;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BeneficiaryAccountCommandService implements
        BaseCommandEntityService<BeneficiaryAccount,UUID , BeneficiaryAccountQueryDTO, BeneficiaryAccountCreateDTO, BeneficiaryAccountUpdateDTO, String> {

    private final BeneficiaryAccountRepository beneficiaryAccountRepository;
    private final BeneficiaryAccountMapper beneficiaryAccountMapper;
    private final CustomerRepository customerRepository;
    private final BranchQueryService branchQueryService;
    private final BankQueryService bankQueryService;
    @Override
    public BeneficiaryAccount saveCreate(BeneficiaryAccount data) {
        if (data.getBranch()==null){
            data.setBranch(data.getCustomer().getBranch());
        }
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount saveUpdate(BeneficiaryAccount data) {
        if (data.getBranch()==null){
            data.setBranch(data.getCustomer().getBranch());
        }
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount convertFromCreateDTO(BeneficiaryAccountCreateDTO dto) {
        BeneficiaryAccount entity = beneficiaryAccountMapper.createDtoToEntity(dto);
        val validations = new ArrayList<ErrorValidation>();
        entity.setBranch(Optional.ofNullable(branchQueryService.getForRelation(dto.getBranch(), validations, "branch")).orElse(entity.getBranch()));
        entity.setBank(Optional.ofNullable(bankQueryService.getForRelation(dto.getBank(),validations,"bank")).orElse(entity.getBank()));
        // Fetch related Customer entity and set it
        if (dto.getCustomerId()!=null) {
            entity.setCustomer(customerRepository.findById(UUID.fromString(dto.getCustomerId()))
                    .orElseGet(() -> {
                        validations.add(ErrorValidation.New("Customer not found", "customer_id", dto.getCustomerId()));
                        return null;
                    }));
        }else {
            validations.add(ErrorValidation.New("Customer can't not null","customer_id",null));
        }
        if (entity.getBank()==null) {
            validations.add(ErrorValidation.New("Bank not found", "bank", null));
        }
        if (validations.size() > 0) {
            throw new BadRequestException(
                    "Validation error",
                    validations.toArray(new ErrorValidation[validations.size()])
            );
        }
        return entity;
    }

    public BeneficiaryAccount convertFromUpdateDTO(String id,BeneficiaryAccountUpdateDTO dto) {
        BeneficiaryAccount entity = beneficiaryAccountRepository.findById(convertId(id))
                .orElseThrow(() -> new BadRequestException("Beneficiary Account not found"));
        beneficiaryAccountMapper.updateEntityFromDto(dto, entity);
        val validations = new ArrayList<ErrorValidation>();
        entity.setBranch(branchQueryService.getForRelation(dto.getBranch(), validations, "branch"));
        entity.setBank(bankQueryService.getForRelation(dto.getBank(),validations,"bank"));
        if(validations.size() > 0) {
            throw new BadRequestException(
                    "Validation error",
                    validations.toArray(new ErrorValidation[validations.size()])
            );
        }
        return entity;
    }

    @Override
    public BeneficiaryAccountQueryDTO convertToResultDTO(BeneficiaryAccount entity) {
        return beneficiaryAccountMapper.entityToQueryDto(entity);
    }

    @Override
    public void delete(UUID id) {
        beneficiaryAccountRepository.deleteById(id);
    }

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }
}
