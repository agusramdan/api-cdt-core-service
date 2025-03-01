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

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionService {
    private final ServiceTransactionRepository repository;
    private final GatewayService gatewayService;
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public  ServiceTransaction prepare(TrxDeposit deposit) {
        val trx = new ServiceTransaction();
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

