package agus.ramdan.cdt.core.master.service.customercrew;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.CustomerCrewDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerCrewRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerCrewQueryService implements
        BaseQueryEntityService<CustomerCrew, UUID, CustomerCrewQueryDTO,String> {
    @Getter
    private final CustomerCrewRepository repository;
    private final CustomerCrewMapper mapper;

    @Override
    public CustomerCrewQueryDTO convertOne(CustomerCrew entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CustomerCrewQueryDTO convert(CustomerCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    public CustomerCrewQueryDTO getByUsername(String username){
        return repository.findByUsername(username)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Crew username not found"));
    }
    public CustomerCrew getForRelation(final CustomerCrewDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"branch":key;
        CustomerCrew data = null;
        if (dto != null) {
            if (dto.getId() != null) {
                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Customer Crew not found",keyField+".id", dto.getId()));
                    return null;
                });
            }
            else {
                data = repository.findByUsername(dto.getUsername()).orElseGet( () -> {
                    validations.add(ErrorValidation.New("Customer Crew not found",keyField+".username", dto.getUsername()));
                    return null;
                });
            }
        }
        return data;
    }
}
