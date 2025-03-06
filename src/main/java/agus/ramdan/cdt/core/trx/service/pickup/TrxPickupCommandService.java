package agus.ramdan.cdt.core.trx.service.pickup;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxPickupMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxPickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrxPickupCommandService implements
        BaseCommandEntityService<TrxPickup, UUID, TrxPickupQueryDTO, TrxPickupCreateDTO, TrxPickupUpdateDTO, String> {

    private final TrxPickupRepository repository;
    private final TrxPickupMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public TrxPickup saveCreate(TrxPickup data) {
        return repository.save(data);
    }

    @Override
    public TrxPickup saveUpdate(TrxPickup data) {
        return repository.save(data);
    }

    @Override
    public TrxPickup convertFromCreateDTO(TrxPickupCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public TrxPickup convertFromUpdateDTO(String id, TrxPickupUpdateDTO dto) {
        TrxPickup trxPickup = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("TrxPickup not found"));
        mapper.updateEntityFromUpdateDto(dto, trxPickup);
        return trxPickup;
    }

    @Override
    public TrxPickupQueryDTO convertToResultDTO(TrxPickup entity) {
        return mapper.entityToQueryDto(entity);
    }
}
