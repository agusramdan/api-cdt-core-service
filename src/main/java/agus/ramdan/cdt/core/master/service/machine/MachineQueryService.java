package agus.ramdan.cdt.core.master.service.machine;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.mapping.MachineMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MachineQueryService implements
        BaseQueryEntityService<Machine, UUID, MachineQueryDTO, String> {

    private final MachineRepository repository;
    private final MachineMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public JpaRepository<Machine, UUID> getRepository() {
        return repository;
    }

    @Override
    public MachineQueryDTO convertOne(Machine entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public MachineQueryDTO convert(Machine entity) {
        return mapper.entityToQueryDto(entity);
    }

    public MachineQueryDTO getByCode(String code) {
        return repository.findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
    }

    @Override
    public Optional<Machine> findByCode(String code) {
        return repository.findByCode(code);
    }
}
