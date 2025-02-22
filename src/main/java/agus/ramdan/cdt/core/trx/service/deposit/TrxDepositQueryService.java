package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrxDepositQueryService {
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
}

