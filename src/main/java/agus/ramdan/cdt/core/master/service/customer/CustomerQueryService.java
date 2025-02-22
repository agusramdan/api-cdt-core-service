package agus.ramdan.cdt.core.master.service.customer;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class CustomerQueryService implements
        BaseQueryEntityService<Customer, UUID, CustomerQueryDTO,UUID> {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public UUID convertId(UUID uuid) {
        return uuid;
    }

    @Override
    public CustomerQueryDTO convertOne(Customer entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CustomerQueryDTO convert(Customer entity) {
        return mapper.entityToQueryDto(entity);
    }

}
