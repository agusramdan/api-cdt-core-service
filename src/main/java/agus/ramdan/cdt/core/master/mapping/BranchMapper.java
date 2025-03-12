package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.branch.BranchCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    Branch createDtoToEntity(BranchCreateDTO dto);

    //    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    BranchQueryDTO entityToQueryDto(Branch entity);

    //    @Mapping(source = "id", target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(BranchUpdateDTO dto, @MappingTarget Branch entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
//    default String map(UUID value) {
//        return value != null ? value.toString() : null;
//    }
}
