package agus.ramdan.cdt.core.master.service.location;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationQueryDTO;
import agus.ramdan.cdt.core.master.mapping.ServiceLocationMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceLocation;
import agus.ramdan.cdt.core.master.persistence.repository.ServiceLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceLocationQueryService implements
        BaseQueryEntityService<ServiceLocation, UUID, ServiceLocationQueryDTO, String> {

    private final ServiceLocationRepository repository;
    private final ServiceLocationMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public JpaRepository<ServiceLocation, UUID> getRepository() {
        return repository;
    }

    @Override
    public ServiceLocationQueryDTO convertOne(ServiceLocation entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public ServiceLocationQueryDTO convert(ServiceLocation entity) {
        return mapper.entityToQueryDto(entity);
    }
}
