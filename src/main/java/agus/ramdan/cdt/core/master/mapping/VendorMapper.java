package agus.ramdan.cdt.core.master.mapping;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Vendor;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VendorMapper {

//    @Mapping(source = "id", target = "id", ignore = true)
    Vendor createDtoToEntity(VendorCreateDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    VendorQueryDTO entityToQueryDto(Vendor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(VendorUpdateDTO dto, @MappingTarget Vendor entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
