package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceLocation;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceLocationMapper {

    @Mapping(target = "id", ignore = true)
    ServiceLocation createDtoToEntity(ServiceLocationCreateDTO dto);

    @Mapping(source = "id", target = "id")
    ServiceLocationQueryDTO entityToQueryDto(ServiceLocation entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(ServiceLocationUpdateDTO dto, @MappingTarget ServiceLocation entity);

    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
