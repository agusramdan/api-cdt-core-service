package agus.ramdan.cdt.core.master.service.product;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.mapping.ServiceProductMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import agus.ramdan.cdt.core.master.persistence.repository.ServiceProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class ServiceProductQueryService implements
        BaseQueryEntityService<ServiceProduct, UUID, ServiceProductQueryDTO, String>
{
    private final ServiceProductRepository repository;
    private final ServiceProductMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public ServiceProductQueryDTO convertOne(ServiceProduct entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public ServiceProductQueryDTO convert(ServiceProduct entity) {
        return mapper.entityToQueryDto(entity);
    }
}
