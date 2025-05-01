package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.utils.EntityFallbackFactory;
import lombok.val;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BeneficiaryAccountMapper {
    @Autowired
    private CustomerMapper customerMapper;
    //@Mapping(source = "customer.id", target = "customerId")
    public abstract BeneficiaryAccountQueryDTO entityToQueryDto(BeneficiaryAccount beneficiaryAccount);

    @AfterMapping
    public void handleException(@MappingTarget BeneficiaryAccountQueryDTO dto, BeneficiaryAccount entity) {
        val customer = EntityFallbackFactory.safe(entity.getCustomer());
        if (customer != null && customer.getId() != null) {
            dto.setCustomerId(customer.getId().toString());
        }
    }
    public abstract BeneficiaryAccount createDtoToEntity(BeneficiaryAccountCreateDTO createDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccountUpdateDTO updateDTO, @MappingTarget BeneficiaryAccount entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  void updateEntityFromDto(BeneficiaryAccount target, @MappingTarget BeneficiaryAccount entity);
}
