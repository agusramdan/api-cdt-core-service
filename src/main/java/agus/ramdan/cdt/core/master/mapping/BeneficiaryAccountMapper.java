package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BeneficiaryAccountMapper {

    @Mapping(source = "customer.id", target = "customerId")
    BeneficiaryAccountQueryDTO entityToQueryDto(BeneficiaryAccount beneficiaryAccount);

    @Mapping(target ="auditMetadata", ignore = true )
    BeneficiaryAccount createDtoToEntity(BeneficiaryAccountCreateDTO createDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BeneficiaryAccountUpdateDTO updateDTO, @MappingTarget BeneficiaryAccount entity);

    @Mapping(target ="auditMetadata", ignore = true )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BeneficiaryAccount target, @MappingTarget BeneficiaryAccount entity);
}
