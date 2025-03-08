package agus.ramdan.cdt.core.trx.service.transfer;

import agus.ramdan.cdt.core.trx.persistence.domain.*;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxTransferRepository;
import agus.ramdan.cdt.core.trx.service.gateway.GatewayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrxTransferService {

    private final TrxTransferRepository repository;

    private final GatewayService gatewayService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public  TrxTransfer prepare(ServiceTransaction transaction) {
        val transfer = new TrxTransfer();
        transfer.setStatus(TrxTransferStatus.PREPARE);
        transfer.setTransaction(transaction);
        return repository.save(transfer);
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public TrxTransfer transferFund(TrxTransfer transfer) {
        transfer = gatewayService.setupGateway(transfer);
        transfer = repository.save(transfer);
        transfer = gatewayService.transferFund(transfer);
        transfer = repository.save(transfer);
        return transfer;
    }

}

