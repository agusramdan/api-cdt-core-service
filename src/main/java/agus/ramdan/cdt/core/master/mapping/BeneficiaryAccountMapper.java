package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.base.service.QueryDTOMapper;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BeneficiaryAccountMapper implements QueryDTOMapper<BeneficiaryAccountQueryDTO,BeneficiaryAccount > {
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerDTO mapCustomerDTO(Customer source) {
        return customerMapper.entityToDto(source);
    }
    public BeneficiaryAccountDTO entityToDto(BeneficiaryAccount beneficiaryAccount){
        beneficiaryAccount = EntityFallbackFactory.safe(beneficiaryAccount);
        if (beneficiaryAccount == null) {
            return null;
        }
        return updateEntityToDto( new BeneficiaryAccountDTO(), beneficiaryAccount);
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract BeneficiaryAccountDTO updateEntityToDto(@MappingTarget BeneficiaryAccountDTO dto, BeneficiaryAccount entity);
    public BeneficiaryAccountQueryDTO entityToQueryDto(BeneficiaryAccount entity){
        entity=EntityFallbackFactory.safe(entity);
        return  entity != null ? updateEntityToDto( new BeneficiaryAccountQueryDTO(), entity):null;
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //@Mapping(source = "customer.id", target = "customerId" , qualifiedByName = {"entityCustomerId","stringToUUID"})
    public abstract BeneficiaryAccountQueryDTO updateEntityToDto(@MappingTarget BeneficiaryAccountQueryDTO dto, BeneficiaryAccount entity);
    @AfterMapping
    public void afterMappingCustomer(@MappingTarget BeneficiaryAccountQueryDTO dto) {
        if (dto.getCustomer() != null) {
            dto.setCustomerId(dto.getCustomer().getId());
        }
    }
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract BeneficiaryAccount createDtoToEntity(BeneficiaryAccountDTO createDTO);
    public abstract BeneficiaryAccount createDtoToEntity(BeneficiaryAccountCreateDTO createDTO);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccountUpdateDTO updateDTO, @MappingTarget BeneficiaryAccount entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccount target, @MappingTarget BeneficiaryAccount entity);
}
