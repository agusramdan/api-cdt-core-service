package agus.ramdan.cdt.core.master.service.product;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.mapping.ServiceProductMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import agus.ramdan.cdt.core.master.persistence.repository.ServiceProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class ServiceProductQueryService implements
        BaseQueryEntityService<ServiceProduct, UUID, ServiceProductQueryDTO, String> {
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

    public ServiceProductQueryDTO getByCode(String code) {
        return findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Code not found"));
    }
//
//    public ServiceProduct getForRelation(final ServiceProductDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
//        final String keyField = key == null ? "product" : key;
//        ServiceProduct data = null;
//        if (dto != null) {
//            if (dto.getId() != null) {
//                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
//                    validations.add(ErrorValidation.New("Product not found", keyField, dto.getId()));
//                    return null;
//                });
//            } else {
//                data = repository.findByCode(dto.getCode()).orElseGet(() -> {
//                    validations.add(ErrorValidation.New("Product not found", keyField, dto.getCode()));
//                    return null;
//                });
//            }
//        }
//        return data;
//    }

}
