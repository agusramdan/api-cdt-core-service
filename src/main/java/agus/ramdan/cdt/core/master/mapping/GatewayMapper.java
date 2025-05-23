package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GatewayMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    //@Mapping(source = "partnerId", target = "partner.id", ignore = true)
    Gateway createDtoToEntity(GatewayCreateDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "partner.id", target = "partnerId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GatewayQueryDTO entityToQueryDto(Gateway entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        //@Mapping(source = "partnerId", target = "partner.id", ignore = true)
    void updateEntityFromUpdateDto(GatewayUpdateDTO dto, @MappingTarget Gateway entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
