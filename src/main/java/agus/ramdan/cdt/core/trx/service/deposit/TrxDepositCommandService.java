package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrxDepositCommandService {
    private final TrxDepositRepository repository;
    private final TrxDepositMapper trxDepositMapper;

    public TrxDepositQueryDTO createTrxDeposit(TrxDepositCreateDTO dto) {
        val option_trx = repository.findByTokenAndSignature(dto.getToken(), dto.getSignature());
        if (option_trx.isPresent()){
            log.info("Resend detected Token and Signature");
        }
        return option_trx.or(() -> {
            TrxDeposit trxDeposit = trxDepositMapper.toEntity(dto);
            return Optional.of(repository.save(trxDeposit));
        }).map(trxDepositMapper::entityToQueryDto).orElse(null);
    }

    public TrxDepositQueryDTO updateTrxDeposit(TrxDepositUpdateDTO dto) {
        TrxDeposit trxDeposit = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        trxDeposit.setStatus(dto.getStatus());
        return trxDepositMapper.entityToQueryDto(repository.save(trxDeposit));
    }

    public void deleteTrxDeposit(UUID id) {
        repository.deleteById(id);
    }
}

