package agus.ramdan.cdt.core.master.service.customer;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerCommandService implements
        BaseCommandService<CustomerQueryDTO, CustomerCreateDTO, CustomerUpdateDTO, UUID>,
        BaseCommandEntityService<Customer,UUID, CustomerQueryDTO, CustomerCreateDTO, CustomerUpdateDTO, UUID> {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final BranchQueryService branchQueryService;
    @Override
    public Customer saveCreate(Customer data) {
        return repository.save(data);
    }

    @Override
    public Customer saveUpdate(Customer data) {
        return repository.save(data);
    }

    @Override
    public Customer convertFromCreateDTO(CustomerCreateDTO dto) {
        val entity= mapper.createDtoToEntity(dto);
        val validations = new ArrayList<ErrorValidation>();
        entity.setBranch(Optional.ofNullable(branchQueryService.getForRelation(dto.getBranch(), validations, "branch")).orElse(entity.getBranch()));
        if (validations.size() > 0) {
            throw new BadRequestException("Validation error",validations);
        }
        return entity;
    }

    @Override
    public Customer convertFromUpdateDTO(UUID id,CustomerUpdateDTO dto) {
        Customer entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Use MapStruct to update only non-null fields
        mapper.updateEntityFromUpdateDto(dto, entity);
        return entity;
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
