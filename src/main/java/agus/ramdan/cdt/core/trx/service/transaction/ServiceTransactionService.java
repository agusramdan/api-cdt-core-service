package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxTransferRepository;
import agus.ramdan.cdt.core.trx.service.transfer.TrxTransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionService {
    private final TrxTransferRepository trxTransferRepository;
    private static final String CHARACTERS = "0123456789";
    private static final int STRING_LENGTH = 20;
    private static final Random random = new Random();

    public String generateRandomString(String input) {
        StringBuilder result = new StringBuilder(STRING_LENGTH);
        result.append(input);
        for (int i = input.length(); i < STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }
    private final ServiceTransactionRepository repository;
    private final TrxTransferService transferService;
//    private final GatewayService gatewayService;
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public  ServiceTransaction prepare(BigDecimal amount) {
        val trx = new ServiceTransaction();
        trx.setAmount(amount);
        trx.setStatus(TrxStatus.PREPARE);
        while (true) {
            trx.setNo(generateRandomString(Long.toString(Instant.now().toEpochMilli())));
            try {
                log.info("prepare {}",trx.getNo());
                return repository.save(trx);
            } catch (Exception e) {
                log.info("duplicate {}",trx.getNo());
            }
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public  ServiceTransaction prepare(TrxDeposit deposit) {
        val trx = prepare(deposit.getAmount());
        trx.setStatus(TrxStatus.CDM_DEPOSIT);
        trx.setDeposit(deposit);
        trx.setBeneficiaryAccount(deposit.getBeneficiaryAccount());
        return repository.save(trx);
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceTransaction executeTransaction(ServiceTransaction trx) {
        log.info("Transfer Transaction; id={}; amount={}; trx={};",trx.getId(),trx.getAmount(),trx.getNo());
        var transfer = transferService.prepare(trx);
        trx.setTransfer(transfer);
        trx.setStatus(TrxStatus.TRANSFER);
        repository.save(trx);
        transfer = transferService.transferFund(transfer);
        trx.setStatus(switch (transfer.getStatus()){
            case SUCCESS -> TrxStatus.TRANSFER_SUCCESS;
            case REVERSAL -> TrxStatus.TRANSFER_REVERSAL;
            case FAILED -> TrxStatus.TRANSFER_FAILED;
            default -> TrxStatus.TRANSFER;
        });
        trx = repository.save(trx);
        return trx;
    }

}

