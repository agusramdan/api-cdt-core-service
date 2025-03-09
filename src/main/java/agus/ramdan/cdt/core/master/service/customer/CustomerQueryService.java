package agus.ramdan.cdt.core.master.service.customer;

import agus.ramdan.base.dto.TID;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class CustomerQueryService implements
        BaseQueryEntityService<Customer, UUID, CustomerQueryDTO,String> {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public CustomerQueryDTO convertOne(Customer entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CustomerQueryDTO convert(Customer entity) {
        return mapper.entityToQueryDto(entity);
    }

    public Customer getForRelation(final CustomerDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"bank":key;
        val validation = new ArrayList<ErrorValidation>();
        Customer data = null;
        if (dto != null) {
            if (dto.getId() != null) {
                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
                    validation.add(ErrorValidation.New("Customer not found",keyField+".id", dto.getId()));
                    return null;
                });
            } else {
                data = repository.findByKtp(dto.getRef()).orElseGet( () -> {
                    validation.add(ErrorValidation.New("Customer not found",keyField+".ref", dto.getRef()));
                    return null;
                });
            }
        }
        return data;
    }

    @Override
    public Customer getForRelation(TID<String> tid, List<ErrorValidation> validations, String key) {
        if (tid instanceof CustomerDTO) return this.getForRelation((CustomerDTO) tid,validations,key);
        return BaseQueryEntityService.super.getForRelation(tid, validations, key);
    }
}
