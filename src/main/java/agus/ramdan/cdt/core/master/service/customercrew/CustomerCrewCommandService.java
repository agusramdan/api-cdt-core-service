package agus.ramdan.cdt.core.master.service.customercrew;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerCrewRepository;
import agus.ramdan.cdt.core.master.service.MasterDataEventProducer;
import agus.ramdan.cdt.core.master.service.customer.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerCrewCommandService extends MasterDataEventProducer<CustomerCrew, UUID, CustomerCrewQueryDTO, CustomerCrewCreateDTO, CustomerCrewUpdateDTO, String> {

    private final CustomerCrewRepository repository;
    private final CustomerCrewMapper mapper;
//    private final CustomerRepository customerRepository;
    private final CustomerQueryService customerQueryService;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public CustomerCrew saveCreate(CustomerCrew data) {

        return repository.save(data);
    }

    @Override
    public CustomerCrew saveUpdate(CustomerCrew data) {
        return repository.save(data);
    }

    @Override
    public CustomerCrew convertFromCreateDTO(CustomerCrewCreateDTO dto) {
        val entity = mapper.createDtoToEntity(dto);
        val validations = new ArrayList<ErrorValidation>();
//        entity.setBranch(branchQueryService.getForRelation(dto.getBranch(), validations, "branch"));
        // Fetch related Customer entity and set it
//        customerQueryService.relation(dto.getCustomerId(), d -> ErrorValidation.add(validations, "Customer not found", "customer_id", d))
//                .or(() -> customerQueryService.relation(dto.getCustomer(), validations, "customer")).ifPresentOrElse(entity::setCustomer, () -> ErrorValidation.add(validations, "Customer can't not null", "customer", null));
        customerQueryService.relation(dto.getCustomer(), validations, "customer").ifPresentOrElse(entity::setCustomer, () -> ErrorValidation.add(validations, "Customer can't not null", "customer", null));
        Customer customer=EntityFallbackFactory.ensureNotLazy(validations,"Deleted Customer","customer", entity::getCustomer);
        if(customer == null){
            ErrorValidation.add(validations, "Customer can't not null", "customer", null);
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
        return entity;
    }

    @Override
    public CustomerCrew convertFromUpdateDTO(String id, CustomerCrewUpdateDTO dto) {
        val customerCrew = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Customer Crew not found"));
        mapper.updateEntityFromUpdateDto(dto, customerCrew);
        val validations = new ArrayList<ErrorValidation>();
        customerQueryService.relation(dto.getCustomer(), validations, "customer").ifPresent(customerCrew::setCustomer);
        Customer customer=EntityFallbackFactory.ensureNotLazy(validations,"Deleted Customer","customer", customerCrew::getCustomer);
        if(customer == null){
            ErrorValidation.add(validations, "Customer can't not null", "customer", null);
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
        return customerCrew;
    }

    @Override
    public CustomerCrewQueryDTO convertToResultDTO(CustomerCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
}
