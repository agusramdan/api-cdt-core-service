package agus.ramdan.cdt.core.master.service.branch;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BranchMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.repository.BranchRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class BranchQueryService implements BaseQueryEntityService<Branch,UUID, BranchQueryDTO, UUID>{

    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public UUID convertId(UUID uuid) {
        return uuid;
    }

    public BranchQueryDTO convertOne(Branch entity) {
        log.debug("Converting single Branch entity to DTO: {}", entity.getId());
        return mapper.entityToQueryDto(entity);
    }

    public BranchQueryDTO convert(Branch entity) {
        log.debug("Converting Branch entity to DTO: {}", entity.getId());
        return mapper.entityToQueryDto(entity);
    }
}
