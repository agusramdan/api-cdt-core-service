package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.machine.MachineCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    Machine createDtoToEntity(MachineCreateDTO dto);

    //@Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    MachineQueryDTO entityToQueryDto(Machine entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(MachineUpdateDTO dto, @MappingTarget Machine entity);

    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
