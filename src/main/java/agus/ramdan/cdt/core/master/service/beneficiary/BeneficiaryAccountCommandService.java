package agus.ramdan.cdt.core.master.service.beneficiary;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.repository.BeneficiaryAccountRepository;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BeneficiaryAccountCommandService implements
        BaseCommandService<BeneficiaryAccountQueryDTO, BeneficiaryAccountCreateDTO, BeneficiaryAccountUpdateDTO, UUID>,
        BaseCommandEntityService<BeneficiaryAccount,UUID , BeneficiaryAccountQueryDTO, BeneficiaryAccountCreateDTO, BeneficiaryAccountUpdateDTO, UUID> {

    private final BeneficiaryAccountRepository beneficiaryAccountRepository;
    private final BeneficiaryAccountMapper beneficiaryAccountMapper;
    private final CustomerRepository customerRepository;
    @Override
    public BeneficiaryAccount saveCreate(BeneficiaryAccount data) {
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount saveUpdate(BeneficiaryAccount data) {
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount convertFromCreateDTO(BeneficiaryAccountCreateDTO createDTO) {
        BeneficiaryAccount entity = beneficiaryAccountMapper.createDtoToEntity(createDTO);

        // Fetch related Customer entity and set it
        if (createDTO.getCustomerId()!=null)
            entity.setCustomer(customerRepository.findById(UUID.fromString(createDTO.getCustomerId()))
                    .orElseThrow(() -> new BadRequestException("Customer not found")));

        return entity;
    }

    public BeneficiaryAccount convertFromUpdateDTO(UUID id,BeneficiaryAccountUpdateDTO updateDTO) {
        BeneficiaryAccount entity = beneficiaryAccountRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Beneficiary Account not found"));

        beneficiaryAccountMapper.updateEntityFromDto(updateDTO, entity);

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
    public UUID convertId(UUID uuid) {
        return uuid;
    }
}
