package agus.ramdan.cdt.core.master.service.gateway;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.mapping.GatewayMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import agus.ramdan.cdt.core.master.persistence.repository.GatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatewayQueryService implements
        BaseQueryEntityService<Gateway, UUID, GatewayQueryDTO, String> {

    private final GatewayRepository repository;
    private final GatewayMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public JpaRepository<Gateway, UUID> getRepository() {
        return repository;
    }

    @Override
    public GatewayQueryDTO convertOne(Gateway entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public GatewayQueryDTO convert(Gateway entity) {
        return mapper.entityToQueryDto(entity);
    }
}
