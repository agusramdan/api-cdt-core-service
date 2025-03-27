package agus.ramdan.cdt.core.trx.service.transfer;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.Propagation5xxException;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxTransferRepository;
import agus.ramdan.cdt.core.trx.service.gateway.GatewayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrxTransferService {

    private final TrxTransferRepository repository;
    private final GatewayService gatewayService;
    private final KafkaTemplate<String, DataEvent> kafkaTemplate;
    public void publishDataEvent(DataEvent dataEvent) {
        kafkaTemplate.send("core-trx-event", dataEvent);
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public TrxTransfer prepare(ServiceTransaction transaction) {
        val transfer = new TrxTransfer();
        transfer.setBeneficiaryAccount(transaction.getBeneficiaryAccount());
        transfer.setStatus(TrxTransferStatus.PREPARE);
        transfer.setTransaction(transaction);
        transfer.setAmount(transaction.getAmount());
        return repository.save(transfer);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public TrxTransfer transferFund(TrxTransfer transfer) {
        boolean isNew = TrxTransferStatus.PREPARE.equals(transfer.getStatus());
        transfer = gatewayService.setupGateway(transfer);
        transfer = repository.save(transfer);
        try {
            transfer = gatewayService.transferFund(transfer);
            transfer = repository.save(transfer);
        } catch (Propagation5xxException e) {
            transfer.setStatus(TrxTransferStatus.GATEWAY_TIME_OUT);
            repository.save(transfer);
            throw e;
        }finally {
            publishDataEvent(DataEvent.builder()
                    .data(transfer)
                    .dataType(transfer.getClass().getCanonicalName())
                    .eventType(isNew ? EventType.CREATE : EventType.UPDATE)
                    .build());
        }
        return transfer;
    }
}

