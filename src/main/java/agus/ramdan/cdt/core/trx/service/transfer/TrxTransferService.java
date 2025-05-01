package agus.ramdan.cdt.core.trx.service.transfer;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.dto.GatewayCallbackDTO;
import agus.ramdan.base.exception.Propagation5xxException;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxTransferRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.gateway.GatewayService;
import agus.ramdan.base.dto.TransactionCheckStatusDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrxTransferService {

    private final TrxTransferRepository repository;
    private final TrxDataEventProducerService producerService;
    private final GatewayService gatewayService;
    private final KafkaTemplate<String, TransactionCheckStatusDTO> kafkaTemplate;

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
            producerService.publishDataEvent(isNew ? EventType.CREATE : EventType.UPDATE, transfer);
        }
        return transfer;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public TrxTransfer transferUpdateStatus(TrxTransfer transfer,String status, String message) {
        transfer.setStatus(gatewayService.mapGatewayStatus(status));
        producerService.publishDataEvent(EventType.UPDATE, transfer);
        return transfer;
    }

    public TrxTransferStatus mapGatewayStatus(String status) {
        return gatewayService.mapGatewayStatus(status);
    }

    @KafkaListener(topics = "gateway-callback-topic", groupId = "cdt-core-transaction-customer-gateway-callback")
    @Transactional()
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
        repository.findByTransactionNo(transactionNo)
                .ifPresentOrElse(transfer -> {
                    Hibernate.initialize(transfer.getGateway());
                    if (gatewayCode != null && !gatewayCode.equals(transfer.getGateway().getCode())) {
                        log.error("Gateway Info  not found: {}", transfer.getTransaction().getNo());
                        return;
                    }
                    transferUpdateStatus(transfer, trx_status, trx_message);
                    val trx = transfer.getTransaction();
                    val trxStatus = new TransactionCheckStatusDTO();
                    trxStatus.setId(trx.getId());
                    trxStatus.setSource("transfer");
                    trxStatus.setMessage(trx_message);
                    kafkaTemplate.send("core-trx-status-check-event",trxStatus);
                }, () -> log.error("Transaction not found: {}", trx_no));

//        val opt = repository.findByNo(transactionNo);
//        if (opt.isEmpty()) {
//            log.error("Transaction not found: {}", transactionNo);
//            return;
//        }
//        ServiceTransaction trx = opt.get();
//        Hibernate.initialize(trx.getTransfer());
//        var transfer = trx.getTransfer();
//        if (transfer == null) {
//            log.error("Transfer not found: {}", transactionNo);
//            return;
//        }
//        Hibernate.initialize(transfer.getGateway());
//        if (gatewayCode != null && !gatewayCode.equals(transfer.getGateway().getCode())) {
//            log.error("Gateway Info  not found: {}", transactionNo);
//        }
//
//        transfer = transferService.transferUpdateStatus(transfer, status, message);
//        trx.setStatus(determineTransactionStatus(transfer.getStatus(),trx.getStatus()));
//        trx = repository.save(trx);
//        switch (trx.getStatus()) {
//            case SUCCESS, TRANSFER_SUCCESS:
//                Hibernate.initialize(trx.getDeposit());
//                val deposit = trx.getDeposit();
//                if (deposit != null) {
//                    deposit.setStatus(TrxDepositStatus.SUCCESS);
//                }
//                break;
//        }
//        producerService.publishDataEvent(EventType.UPDATE,trx);
    }
}

