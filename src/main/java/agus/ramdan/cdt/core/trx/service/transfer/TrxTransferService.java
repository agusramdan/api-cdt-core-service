package agus.ramdan.cdt.core.trx.service.transfer;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.dto.GatewayCallbackDTO;
import agus.ramdan.base.dto.TransactionCheckStatusDTO;
import agus.ramdan.base.exception.Propagation5xxException;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxTransferRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.gateway.GatewayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional( dontRollbackOn = Exception.class)
public class TrxTransferService {

    private final TrxTransferRepository repository;
    private final TrxDataEventProducerService producerService;
    private final GatewayService gatewayService;
    private final KafkaTemplate<String, TransactionCheckStatusDTO> kafkaTemplate;

    public TrxTransfer prepare(ServiceTransaction transaction) {
        val transfer = new TrxTransfer();
        transfer.setBeneficiaryAccount(transaction.getBeneficiaryAccount());
        transfer.setTrxNo(transaction.getNo());
        transfer.setStatus(TrxTransferStatus.PREPARE);
        transfer.setTransaction(transaction);
        transfer.setAmount(transaction.getAmount());
        return repository.saveAndFlush(transfer);
    }

    public TrxTransfer transferFund(TrxTransfer transfer) {
        transfer = gatewayService.setupGateway(transfer);
        transfer = repository.saveAndFlush(transfer);
        try {
            transfer = gatewayService.transferFund(transfer);
        } catch (Propagation5xxException e) {
            transfer.setStatus(TrxTransferStatus.GATEWAY_TIME_OUT);
            throw e;
        }finally {
            transfer = repository.saveAndFlush(transfer);
            producerService.publishDataEvent(EventType.UPDATE, transfer);
        }
        return transfer;
    }

    public TrxTransfer transferUpdateStatus(TrxTransfer transfer,String status, String message) {
        transfer.setStatus(gatewayService.mapGatewayStatus(status));
        producerService.publishDataEvent(EventType.UPDATE, transfer);
        return transfer;
    }

    public TrxTransferStatus mapGatewayStatus(String status) {
        return gatewayService.mapGatewayStatus(status);
    }

    @KafkaListener(topics = "gateway-callback-topic", groupId = "cdt-core-transfer-gateway-callback")
    public void consumeGatewayCallbackDTO(GatewayCallbackDTO event) {
        log.info("consumeGatewayCallbackDTO: {}", event);
        String gatewayCode = event.getGatewayCode();
        String transactionNo = event.getTransactionNo();
        String status = event.getStatus();
        String message = event.getMessage();
        Map<String, Object> dataMap=null;
        if (event.getData() instanceof Map) {
            dataMap = (Map<String, Object>) event.getData();
            if (transactionNo == null) {
                transactionNo = (String) dataMap.get("transaction_no");
            }
            if(status== null){
                status = (String) dataMap.get("status");
            }
            if (message==null){
                message = (String) dataMap.get("message");
            }
        }
        val trx_no = transactionNo;
        val trx_status = status;
        val trx_message = message;
        log.info("gatewayCode: {} ,transactionNo: {} ,status: {} ,message: {}", gatewayCode,transactionNo,status,message);
        Optional<TrxTransfer> trxTransfer = repository.findByTrxNo(trx_no)
                .stream().findFirst();
        if (trxTransfer.isEmpty()) {
            trxTransfer = repository.findByTransaction(transactionNo)
                    .stream().findFirst();
        }
        trxTransfer.ifPresentOrElse(transfer -> {
                    Hibernate.initialize(transfer.getGateway());
                    if (gatewayCode != null && !gatewayCode.equals(transfer.getGateway().getCode())) {
                        log.error("Gateway Info  not found: {}", transfer.getTransaction().getNo());
                        return;
                    }
                    val trx = transfer.getTransaction();
                    if (trx == null) {
                        log.error("Transaction not found: {}", transfer.getTransaction().getNo());
                        return;
                    }
                    if (transfer.getTrxNo()==null) {
                        transfer.setTrxNo(trx_no);
                    }
                    transferUpdateStatus(transfer, trx_status, trx_message);
                    val trxStatus = new TransactionCheckStatusDTO();
                    trxStatus.setId(trx.getId());
                    trxStatus.setSource("transfer");
                    trxStatus.setMessage(trx_message);
                    kafkaTemplate.send("core-trx-status-check-event",trxStatus);
                }, () -> log.error("Transaction not found: {}", trx_no));
    }

}

