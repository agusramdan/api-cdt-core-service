package agus.ramdan.cdt.core.master.service.machine;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.mapping.MachineMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.repository.MachineRepository;
import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Machine getForRelation(final MachineDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"bank":key;
        Machine data = null;
        if (dto != null) {
            if (dto.getId() != null) {
                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Machine not found",keyField+".id", dto.getId()));
                    return null;
                });
            } else {
                data = repository.findByCode(dto.getCode()).orElseGet( () -> {
                    validations.add(ErrorValidation.New("Machine not found",keyField+".code", dto.getCode()));
                    return null;
                });
            }
        }
        return data;
    }
}
