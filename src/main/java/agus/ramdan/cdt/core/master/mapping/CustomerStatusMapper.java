package agus.ramdan.cdt.core.master.mapping;


import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerStatusMapper {

//    @Mapping(source = "id", target = "id", ignore = true)
    CustomerStatus createDtoToEntity(CustomerStatusCreateDTO dto);

    CustomerStatusQueryDTO entityToQueryDto(CustomerStatus entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(CustomerStatusUpdateDTO dto, @MappingTarget CustomerStatus entity);
}

