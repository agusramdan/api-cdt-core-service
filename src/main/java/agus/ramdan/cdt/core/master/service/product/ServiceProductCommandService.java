package agus.ramdan.cdt.core.master.service.product;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.ServiceProductMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import agus.ramdan.cdt.core.master.persistence.repository.ServiceProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceProductCommandService implements
        BaseCommandEntityService<ServiceProduct, UUID, ServiceProductQueryDTO, ServiceProductCreateDTO, ServiceProductUpdateDTO, String> {

    private final ServiceProductRepository repository;
    private final ServiceProductMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ServiceProduct saveCreate(ServiceProduct data) {
        return repository.save(data);
    }

    @Override
    public ServiceProduct saveUpdate(ServiceProduct data) {
        return repository.save(data);
    }

    @Override
    public ServiceProduct convertFromCreateDTO(ServiceProductCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public ServiceProduct convertFromUpdateDTO(String id, ServiceProductUpdateDTO dto) {
        ServiceProduct product = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Service Product not found"));
        mapper.updateEntityFromUpdateDto(dto, product);
        return product;
    }

    @Override
    public ServiceProductQueryDTO convertToResultDTO(ServiceProduct entity) {
        return mapper.entityToQueryDto(entity);
    }
}
