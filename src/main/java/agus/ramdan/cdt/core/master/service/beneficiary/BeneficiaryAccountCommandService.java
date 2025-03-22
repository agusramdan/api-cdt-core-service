package agus.ramdan.cdt.core.master.service.beneficiary;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.repository.BeneficiaryAccountRepository;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import agus.ramdan.cdt.core.master.service.MasterDataEventProducer;
import agus.ramdan.cdt.core.master.service.accounttype.AccountTypeQueryService;
import agus.ramdan.cdt.core.master.service.bank.BankQueryService;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.master.service.countrycode.CountryCodeQueryService;
import agus.ramdan.cdt.core.master.service.customer.CustomerQueryService;
import agus.ramdan.cdt.core.master.service.customerstatus.CustomerStatusQueryService;
import agus.ramdan.cdt.core.master.service.customertype.CustomerTypeQueryService;
import agus.ramdan.cdt.core.master.service.regioncode.RegionCodeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BeneficiaryAccountCommandService extends MasterDataEventProducer<BeneficiaryAccount, UUID, BeneficiaryAccountQueryDTO, BeneficiaryAccountCreateDTO, BeneficiaryAccountUpdateDTO, String> {

    private final BeneficiaryAccountRepository beneficiaryAccountRepository;
    private final BeneficiaryAccountMapper beneficiaryAccountMapper;
    private final CustomerRepository customerRepository;
    private final CustomerQueryService customerQueryService;
    private final BranchQueryService branchQueryService;
    private final BankQueryService bankQueryService;
    private final AccountTypeQueryService accountTypeQueryService;
    private final CountryCodeQueryService countryCodeQueryService;
    private final CustomerStatusQueryService customerStatusQueryService;
    private final CustomerTypeQueryService customerTypeQueryService;
    private final RegionCodeQueryService regionCodeQueryService;

    @Override
    public BeneficiaryAccount saveCreate(BeneficiaryAccount data) {
        if (data.getBranch() == null && data.getCustomer() != null) {
            data.setBranch(data.getCustomer().getBranch());
        }
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount saveUpdate(BeneficiaryAccount data) {
        if (data.getBranch() == null && data.getCustomer() != null) {
            data.setBranch(data.getCustomer().getBranch());
        }
        return beneficiaryAccountRepository.save(data);
    }

    @Override
    public BeneficiaryAccount convertFromCreateDTO(BeneficiaryAccountCreateDTO dto) {
        BeneficiaryAccount entity = beneficiaryAccountMapper.createDtoToEntity(dto);
        val validations = new ArrayList<ErrorValidation>();
        // Fetch related Customer entity and set it
        customerQueryService.relation(dto.getCustomerId(), d -> ErrorValidation.add(validations, "Customer not found", "customer_id", d))
                .or(() -> customerQueryService.relation(dto.getCustomer(), validations, "customer")).ifPresent(entity::setCustomer);
        branchQueryService.relation(dto.getBranch(), validations, "branch").ifPresent(entity::setBranch);
        bankQueryService.relation(dto.getBank(), validations, "bank").ifPresent(entity::setBank);
        accountTypeQueryService.relation(dto.getAccountType(), validations, "account_type").ifPresent(entity::setAccountType);
        countryCodeQueryService.relation(dto.getCountryCode(), validations, "country_code").ifPresent(entity::setCountryCode);
        customerStatusQueryService.relation(dto.getCustomerStatus(), validations, "customer_status").ifPresent(entity::setCustomerStatus);
        customerTypeQueryService.relation(dto.getCustomerType(), validations, "customer_type").ifPresent(entity::setCustomerType);
        regionCodeQueryService.relation(dto.getRegionCode(), validations, "region_code").ifPresent(entity::setRegionCode);
        if (validations.isEmpty()) {
            if (entity.getCustomer() == null) {
                validations.add(ErrorValidation.New("Customer can't not null", "customer_id", null));
            }
            if (entity.getBank() == null) {
                validations.add(ErrorValidation.New("Bank not found", "bank", null));
            }
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
        return entity;
    }

    public BeneficiaryAccount convertFromUpdateDTO(String id, BeneficiaryAccountUpdateDTO dto) {
        BeneficiaryAccount entity = beneficiaryAccountRepository.findById(convertId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary Account not found"));
        val validations = new ArrayList<ErrorValidation>();
        beneficiaryAccountMapper.updateEntityFromDto(dto, entity);
        branchQueryService.relation(dto.getBranch(), validations, "branch").ifPresent(entity::setBranch);
        bankQueryService.relation(dto.getBank(), validations, "bank").ifPresent(entity::setBank);
        accountTypeQueryService.relation(dto.getAccountType(), validations, "account_type").ifPresent(entity::setAccountType);
        countryCodeQueryService.relation(dto.getCountryCode(), validations, "country_code").ifPresent(entity::setCountryCode);
        customerStatusQueryService.relation(dto.getCustomerStatus(), validations, "customer_status").ifPresent(entity::setCustomerStatus);
        customerTypeQueryService.relation(dto.getCustomerType(), validations, "customer_type").ifPresent(entity::setCustomerType);
        regionCodeQueryService.relation(dto.getCustomerStatus(), validations, "region_code").ifPresent(entity::setRegionCode);
        if (validations.isEmpty()) {
            if (entity.getCustomer() == null) {
                validations.add(ErrorValidation.New("Customer can't not null", "customer_id", null));
            }
            if (entity.getBank() == null) {
                validations.add(ErrorValidation.New("Bank not found", "bank", null));
            }
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
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
