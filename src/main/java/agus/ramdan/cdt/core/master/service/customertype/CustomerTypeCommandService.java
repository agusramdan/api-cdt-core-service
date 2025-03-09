package agus.ramdan.cdt.core.master.service.customertype;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerTypeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerType;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerTypeCommandService implements
        BaseCommandEntityService<CustomerType, String, CustomerTypeQueryDTO, CustomerTypeCreateDTO, CustomerTypeUpdateDTO, String> {

    private final CustomerTypeRepository repository;
    private final CustomerTypeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public CustomerType saveCreate(CustomerType data) {
        return repository.save(data);
    }

    @Override
    public CustomerType saveUpdate(CustomerType data) {
        return repository.save(data);
    }

    @Override
    public CustomerType convertFromCreateDTO(CustomerTypeCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public CustomerType convertFromUpdateDTO(String id, CustomerTypeUpdateDTO dto) {
        CustomerType customerType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Type not found"));
        mapper.updateEntityFromUpdateDto(dto, customerType);
        return customerType;
    }

    @Override
    public CustomerTypeQueryDTO convertToResultDTO(CustomerType entity) {
        return mapper.entityToQueryDto(entity);
    }
}

