package agus.ramdan.cdt.core.master.service.gateway;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.GatewayMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import agus.ramdan.cdt.core.master.persistence.repository.GatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatewayCommandService implements
        BaseCommandEntityService<Gateway, UUID, GatewayQueryDTO, GatewayCreateDTO, GatewayUpdateDTO, String> {

    private final GatewayRepository repository;
    private final GatewayMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Gateway saveCreate(Gateway data) {
        return repository.save(data);
    }

    @Override
    public Gateway saveUpdate(Gateway data) {
        return repository.save(data);
    }

    @Override
    public Gateway convertFromCreateDTO(GatewayCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public Gateway convertFromUpdateDTO(String id, GatewayUpdateDTO dto) {
        Gateway gateway = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Gateway not found"));
        mapper.updateEntityFromUpdateDto(dto, gateway);
        return gateway;
    }

    @Override
    public GatewayQueryDTO convertToResultDTO(Gateway entity) {
        return mapper.entityToQueryDto(entity);
    }
}
