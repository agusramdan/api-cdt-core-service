package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.base.service.QueryDTOMapper;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.base.utils.EntityFallbackFactory;
import lombok.val;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class BeneficiaryAccountMapper implements QueryDTOMapper<BeneficiaryAccountQueryDTO,BeneficiaryAccount, String > {
    @Autowired
    private CustomerMapper customerMapper;
    public String convertId(UUID id){
        if (id == null) {
            return null;
        }
        return id.toString();
    }
    public String convertId(String id){
        return id;
    }
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
    public abstract BeneficiaryAccountQueryDTO updateEntityToDto(@MappingTarget BeneficiaryAccountQueryDTO dto, BeneficiaryAccount entity);
    //@Mapping(source = "customer.id", target = "customerId")
    //public abstract BeneficiaryAccountQueryDTO entityToQueryDto(BeneficiaryAccount beneficiaryAccount);
    @AfterMapping
    public void handleException(@MappingTarget BeneficiaryAccountQueryDTO dto, BeneficiaryAccount entity) {
        val customer = dto.getCustomer();
        if (customer != null && customer.getId() != null) {
            dto.setCustomerId(customer.getId());
        }
    }
    public abstract BeneficiaryAccount createDtoToEntity(BeneficiaryAccountCreateDTO createDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccountUpdateDTO updateDTO, @MappingTarget BeneficiaryAccount entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccount target, @MappingTarget BeneficiaryAccount entity);
}
