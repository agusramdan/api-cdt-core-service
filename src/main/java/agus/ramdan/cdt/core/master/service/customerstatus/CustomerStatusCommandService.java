package agus.ramdan.cdt.core.master.service.customerstatus;

import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CustomerStatusMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerStatus;
import agus.ramdan.cdt.core.master.persistence.repository.CustomerStatusRepository;
import agus.ramdan.cdt.core.master.service.MasterDataEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerStatusCommandService extends MasterDataEventProducer<CustomerStatus, String, CustomerStatusQueryDTO, CustomerStatusCreateDTO, CustomerStatusUpdateDTO, String> {

    private final CustomerStatusRepository repository;
    private final CustomerStatusMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public CustomerStatus saveCreate(CustomerStatus data) {
        return repository.save(data);
    }

    @Override
    public CustomerStatus saveUpdate(CustomerStatus data) {
        return repository.save(data);
    }

    @Override
    public CustomerStatus convertFromCreateDTO(CustomerStatusCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public CustomerStatus convertFromUpdateDTO(String id, CustomerStatusUpdateDTO dto) {
        CustomerStatus customerStatus = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Status not found"));
        mapper.updateEntityFromUpdateDto(dto, customerStatus);
        return customerStatus;
    }

    @Override
    public CustomerStatusQueryDTO convertToResultDTO(CustomerStatus entity) {
        return mapper.entityToQueryDto(entity);
    }
}

