package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerTypeMapper {

    //    @Mapping(source = "id", target = "id", ignore = true)
    CustomerType createDtoToEntity(CustomerTypeCreateDTO dto);

    CustomerTypeQueryDTO entityToQueryDto(CustomerType entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(CustomerTypeUpdateDTO dto, @MappingTarget CustomerType entity);
}

