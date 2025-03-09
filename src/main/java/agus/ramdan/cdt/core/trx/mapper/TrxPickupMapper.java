package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupUpdateDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TrxPickupMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    //@Mapping(source = "machineId", target = "machine.id", qualifiedByName = "stringToUUID")
    TrxPickup createDtoToEntity(TrxPickupCreateDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    //@Mapping(source = "machine.id", target = "machineId", qualifiedByName = "uuidToString")
    TrxPickupQueryDTO entityToQueryDto(TrxPickup entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //@Mapping(source = "machineId", target = "machine.id", qualifiedByName = "stringToUUID")
    void updateEntityFromUpdateDto(TrxPickupUpdateDTO dto, @MappingTarget TrxPickup entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}

