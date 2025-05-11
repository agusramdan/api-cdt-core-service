package agus.ramdan.cdt.core.master.service.customertype;


import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerTypeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerType;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerTypeQueryService implements
        BaseQueryEntityService<CustomerType, String, CustomerTypeQueryDTO, String> {

    private final CustomerTypeRepository repository;
    private final CustomerTypeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<CustomerType, String> getRepository() {
        return repository;
    }

    @Override
    public CustomerTypeQueryDTO convertOne(CustomerType entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CustomerTypeQueryDTO convert(CustomerType entity) {
        return mapper.entityToQueryDto(entity);
    }
}
