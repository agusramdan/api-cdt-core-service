package agus.ramdan.cdt.core.master.service.customer;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerCommandService implements
        BaseCommandService<CustomerQueryDTO, CustomerCreateDTO, CustomerUpdateDTO, UUID>,
        BaseCommandEntityService<Customer,UUID, CustomerQueryDTO, CustomerCreateDTO, CustomerUpdateDTO, UUID> {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Customer saveCreate(Customer data) {
        return repository.save(data);
    }

    @Override
    public Customer saveUpdate(Customer data) {
        return repository.save(data);
    }

    @Override
    public Customer convertFromCreateDTO(CustomerCreateDTO createDTO) {
        return mapper.createDtoToEntity(createDTO);
    }

    @Override
    public Customer convertFromUpdateDTO(UUID id,CustomerUpdateDTO updateDTO) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        // Use MapStruct to update only non-null fields
        mapper.updateEntityFromUpdateDto(updateDTO, existingCustomer);
        return existingCustomer;
    }

    @Override
    public CustomerQueryDTO convertToResultDTO(Customer entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public UUID convertId(UUID uuid) {
        return uuid;
    }
}
