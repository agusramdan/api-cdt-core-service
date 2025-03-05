package agus.ramdan.cdt.core.master.service.vendorcrew;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.VendorCrewDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.mapping.VendorCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.VendorCrew;
import agus.ramdan.cdt.core.master.persistence.repository.VendorCrewRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorCrewQueryService implements
        BaseQueryEntityService<VendorCrew, UUID, VendorCrewQueryDTO,String> {
    @Getter
    private final VendorCrewRepository repository;
    private final VendorCrewMapper mapper;

    @Override
    public VendorCrewQueryDTO convertOne(VendorCrew entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public VendorCrewQueryDTO convert(VendorCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    public VendorCrewQueryDTO getByUsername(String username){
        return repository.findByUsername(username)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Crew username not found"));
    }
    public VendorCrew getForRelation(final VendorCrewDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"branch":key;
        VendorCrew data = null;
        if (dto != null) {
            if (dto.getId() != null) {
                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Vendor Crew not found",keyField+".id", dto.getId()));
                    return null;
                });
            }
            else {
                data = repository.findByUsername(dto.getUsername()).orElseGet( () -> {
                    validations.add(ErrorValidation.New("Vendor Crew not found",keyField+".username", dto.getUsername()));
                    return null;
                });
            }
        }
        return data;
    }
}
