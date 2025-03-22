package agus.ramdan.cdt.core.master.service.location;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.ServiceLocationMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceLocation;
import agus.ramdan.cdt.core.master.persistence.repository.ServiceLocationRepository;
import agus.ramdan.cdt.core.master.service.MasterDataEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceLocationCommandService extends MasterDataEventProducer<ServiceLocation, UUID, ServiceLocationQueryDTO, ServiceLocationCreateDTO, ServiceLocationUpdateDTO, String> {

    private final ServiceLocationRepository repository;
    private final ServiceLocationMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ServiceLocation saveCreate(ServiceLocation data) {
        return repository.save(data);
    }

    @Override
    public ServiceLocation saveUpdate(ServiceLocation data) {
        return repository.save(data);
    }

    @Override
    public ServiceLocation convertFromCreateDTO(ServiceLocationCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public ServiceLocation convertFromUpdateDTO(String id, ServiceLocationUpdateDTO dto) {
        ServiceLocation location = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Service Location not found"));
        mapper.updateEntityFromUpdateDto(dto, location);
        return location;
    }

    @Override
    public ServiceLocationQueryDTO convertToResultDTO(ServiceLocation entity) {
        return mapper.entityToQueryDto(entity);
    }
}
