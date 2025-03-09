package agus.ramdan.cdt.core.master.service.customerstatus;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerStatusMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerStatus;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerStatusQueryService implements
        BaseQueryEntityService<CustomerStatus, String, CustomerStatusQueryDTO, String> {

    private final CustomerStatusRepository repository;
    private final CustomerStatusMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<CustomerStatus, String> getRepository() {
        return repository;
    }

    @Override
    public CustomerStatusQueryDTO convertOne(CustomerStatus entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CustomerStatusQueryDTO convert(CustomerStatus entity) {
        return mapper.entityToQueryDto(entity);
    }
}
