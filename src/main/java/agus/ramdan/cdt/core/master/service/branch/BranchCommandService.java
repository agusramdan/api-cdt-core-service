package agus.ramdan.cdt.core.master.service.branch;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.BranchMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchCommandService implements
        BaseCommandService<BranchQueryDTO, BranchCreateDTO, BranchUpdateDTO, String>,
        BaseCommandEntityService<Branch, UUID, BranchQueryDTO, BranchCreateDTO, BranchUpdateDTO, String> {

    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public Branch saveCreate(Branch entity) {
        return repository.save(entity);
    }

    @Override
    public Branch saveUpdate(Branch entity) {
        return repository.save(entity);
    }

    @Override
    public Branch convertFromCreateDTO(BranchCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }
    public Branch getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
    @Override
    public Branch convertFromUpdateDTO(String id, BranchUpdateDTO dto) {
        Branch branch = getById(convertId(id));
        mapper.updateEntityFromUpdateDto(dto, branch);
        return branch;
    }

    @Override
    public BranchQueryDTO convertToResultDTO(Branch entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public void delete(UUID id) {
        getById(id);
        repository.deleteById(id);
    }

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }
}
