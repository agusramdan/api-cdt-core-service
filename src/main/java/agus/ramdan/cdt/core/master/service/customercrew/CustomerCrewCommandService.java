package agus.ramdan.cdt.core.master.service.customercrew;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerCrewRepository;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerRepository;
import agus.ramdan.cdt.core.master.service.customer.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerCrewCommandService implements
        BaseCommandEntityService<CustomerCrew, UUID, CustomerCrewQueryDTO, CustomerCrewCreateDTO, CustomerCrewUpdateDTO, String> {

    private final CustomerCrewRepository repository;
    private final CustomerCrewMapper mapper;
    private final CustomerRepository customerRepository;
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
        val entity =mapper.createDtoToEntity(dto);
        val validations = new ArrayList<ErrorValidation>();
//        entity.setBranch(branchQueryService.getForRelation(dto.getBranch(), validations, "branch"));
        // Fetch related Customer entity and set it

        if (dto.getCustomerId()!=null) {
            entity.setCustomer(customerRepository.findById(UUID.fromString(dto.getCustomerId()))
                    .orElseGet(() -> {
                        validations.add(ErrorValidation.New("Customer not found", "customer_id", dto.getCustomerId()));
                        return null;
                    }));
        } else {
            entity.setCustomer(customerQueryService.getForRelation(dto.getCustomer(),validations,"customer"));
        }
        if(entity.getCustomer()==null){
            validations.add(ErrorValidation.New("Customer can't not null","customer",null));
        }
        if (validations.size() > 0) {
            throw new BadRequestException("Validation error",validations);
        }
        return entity;
    }

    @Override
    public CustomerCrew convertFromUpdateDTO(String id, CustomerCrewUpdateDTO dto) {
        CustomerCrew customerCrew = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Customer Crew not found"));
        mapper.updateEntityFromUpdateDto(dto, customerCrew);
        return customerCrew;
    }

    @Override
    public CustomerCrewQueryDTO convertToResultDTO(CustomerCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
}
