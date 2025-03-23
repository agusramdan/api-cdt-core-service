package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerCrewMapper {

    //    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "customerId", target = "customer.id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    CustomerCrew createDtoToEntity(CustomerCrewCreateDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    @Mapping(source = "customer.id", target = "customerId", qualifiedByName = "uuidToString")
        //@Mapping(source = "user_id", target = "userId", qualifiedByName = "uuidToString")
    CustomerCrewQueryDTO entityToQueryDto(CustomerCrew entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "customerId", target = "customer.id", qualifiedByName = "stringToUUID")
        //@Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    void updateEntityFromUpdateDto(CustomerCrewUpdateDTO dto, @MappingTarget CustomerCrew entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
