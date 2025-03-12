package agus.ramdan.cdt.core.trx.service.pickup;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxPickupMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickupStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxPickupRepository;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrxPickupCommandService implements
        BaseCommandEntityService<TrxPickup, UUID, TrxPickupQueryDTO, TrxPickupCreateDTO, TrxPickupUpdateDTO, String> {

    private final TrxPickupRepository repository;
    private final TrxPickupMapper mapper;
    private final QRCodeQueryService codeQueryService;
    private final MachineQueryService machineQueryService;

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
        val validates = new ArrayList<ErrorValidation>();
        val entity = mapper.createDtoToEntity(dto);
        machineQueryService.relation(dto.getMachine(), validates, "machine").ifPresent(entity::setMachine);
        BadRequestException.ThrowWhenError("Invalid Transaction", validates);
        entity.setStatus(TrxPickupStatus.SUCCESS);
        return entity;
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
