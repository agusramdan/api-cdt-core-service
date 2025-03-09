package agus.ramdan.cdt.core.master.service.branch;

import agus.ramdan.base.dto.TID;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BranchMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.repository.BranchRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class BranchQueryService implements BaseQueryEntityService<Branch,UUID, BranchQueryDTO, String>{

    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    public BranchQueryDTO convertOne(Branch entity) {
        log.debug("Converting single Branch entity to DTO: {}", entity.getId());
        return mapper.entityToQueryDto(entity);
    }

    public BranchQueryDTO convert(Branch entity) {
        log.debug("Converting Branch entity to DTO: {}", entity.getId());
        return mapper.entityToQueryDto(entity);
    }
    public BranchQueryDTO getByCode(String code) {
        return repository.findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Code not found"));
    }

    public Branch getForRelation(final BranchDTO branchDTO, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"branch":key;
        Branch data = null;
        if (branchDTO != null) {
            if (branchDTO.getId() != null) {
                data = repository.findById(convertId(branchDTO.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Branch not found",keyField+".id", branchDTO.getId()));
                    return null;
                });
            } else {
                data = repository.findByCode(branchDTO.getCode()).orElseGet( () -> {
                    validations.add(ErrorValidation.New("Branch not found",keyField+".code", branchDTO.getCode()));
                    return null;
                });
            }
        }
        return data;
    }

    @Override
    public Branch getForRelation(TID<String> tid, List<ErrorValidation> validations, String key) {
        if(tid instanceof BranchDTO){
            return this.getForRelation((BranchDTO) tid,validations,key);
        }
        return BaseQueryEntityService.super.getForRelation(tid, validations, key);
    }
}
