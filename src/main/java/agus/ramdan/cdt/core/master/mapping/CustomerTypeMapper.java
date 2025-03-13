package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerTypeMapper {

    //    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target ="auditMetadata", ignore = true )
    CustomerType createDtoToEntity(CustomerTypeCreateDTO dto);

    CustomerTypeQueryDTO entityToQueryDto(CustomerType entity);
    @Mapping(target ="auditMetadata", ignore = true )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(CustomerTypeUpdateDTO dto, @MappingTarget CustomerType entity);
}

