package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.VendorCrew;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VendorCrewMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "vendorId", target = "vendor.id", qualifiedByName = "stringToUUID")
    @Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    @Mapping(target ="auditMetadata", ignore = true )
    VendorCrew createDtoToEntity(VendorCrewCreateDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    @Mapping(source = "vendor.id", target = "vendorId", qualifiedByName = "uuidToString")
    @Mapping(source = "user_id", target = "userId", qualifiedByName = "uuidToString")
    VendorCrewQueryDTO entityToQueryDto(VendorCrew entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "vendorId", target = "vendor.id", qualifiedByName = "stringToUUID")
    @Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    @Mapping(target ="auditMetadata", ignore = true )
    void updateEntityFromUpdateDto(VendorCrewUpdateDTO dto, @MappingTarget VendorCrew entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}

