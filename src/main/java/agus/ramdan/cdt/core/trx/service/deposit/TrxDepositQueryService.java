package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class TrxDepositQueryService implements
        BaseQueryEntityService<TrxDeposit, UUID, TrxDepositQueryDTO, String> {

    private final TrxDepositRepository repository;
    private final TrxDepositMapper queryMapper;

    public List<TrxDepositQueryDTO> getAllTrxDeposits() {
        return queryMapper.toDtoList(repository.findAll());
    }

    public TrxDepositQueryDTO getTrxDepositById(UUID id) {
        return repository.findById(id)
                .map(queryMapper::entityToQueryDto)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public UUID convertId(String string) {
        return UUID.fromString(string);
    }

    @Override
    public TrxDepositQueryDTO convertOne(TrxDeposit entity) {
        return queryMapper.entityToQueryDto(entity);
    }

    @Override
    public TrxDepositQueryDTO convert(TrxDeposit entity) {
        return queryMapper.entityToQueryDto(entity);
    }
}

