package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceProductMapper {

//    @Mapping(source = "id", target = "id", ignore = true)
    ServiceProduct createDtoToEntity(ServiceProductCreateDTO dto);

//    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    ServiceProductQueryDTO entityToQueryDto(ServiceProduct entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", ignore = true)
    void updateEntityFromUpdateDto(ServiceProductUpdateDTO dto, @MappingTarget ServiceProduct entity);

    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
