package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionService {
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
    private final GatewayService gatewayService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public  ServiceTransaction prepare() {
        val trx = new ServiceTransaction();
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
        val trx = prepare();
        trx.setStatus(TrxStatus.CDM_DEPOSIT);
        trx.setDeposit(deposit);
        return repository.save(trx);
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceTransaction transferFund(ServiceTransaction trx) {
        trx = gatewayService.setupGateway(trx);
        trx = repository.save(trx);
        trx = gatewayService.transferFund(trx);
        trx = repository.save(trx);
        return trx;
    }

}

