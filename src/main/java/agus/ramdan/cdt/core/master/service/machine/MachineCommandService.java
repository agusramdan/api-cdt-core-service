package agus.ramdan.cdt.core.master.service.machine;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.MachineMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MachineCommandService implements
        BaseCommandEntityService<Machine, UUID, MachineQueryDTO, MachineCreateDTO, MachineUpdateDTO, String> {

    private final MachineRepository repository;
    private final MachineMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Machine saveCreate(Machine data) {
        return repository.save(data);
    }

    @Override
    public Machine saveUpdate(Machine data) {
        return repository.save(data);
    }

    @Override
    public Machine convertFromCreateDTO(MachineCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public Machine convertFromUpdateDTO(String id, MachineUpdateDTO dto) {
        Machine machine = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));
        mapper.updateEntityFromUpdateDto(dto, machine);
        return machine;
    }

    @Override
    public MachineQueryDTO convertToResultDTO(Machine entity) {
        return mapper.entityToQueryDto(entity);
    }
}
