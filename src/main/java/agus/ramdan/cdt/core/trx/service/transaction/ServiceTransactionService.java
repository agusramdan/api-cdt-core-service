package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.Propagation5xxException;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.controller.dto.PjpurRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.TransferRuleConfig;
import agus.ramdan.cdt.core.trx.persistence.domain.*;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.pjpur.PjpurService;
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
@Transactional
public class ServiceTransactionService {

    private final ServiceTransactionRepository repository;
    private final TrxTransferService transferService;
    private final TrxDataEventProducerService producerService;
    private final PjpurService pjpurService;
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
    private TrxStatus determineTransactionStatus(TrxTransferStatus transferStatus, TrxStatus defaultStatus) {
        return switch (transferStatus) {
            case SUCCESS -> TrxStatus.TRANSFER_SUCCESS;
            case REVERSAL -> TrxStatus.TRANSFER_REVERSAL;
            case FAILED -> TrxStatus.TRANSFER_FAILED;
            default -> defaultStatus;
        };
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceTransaction prepare(BigDecimal amount) {
        val trx = new ServiceTransaction();
        trx.setAmount(amount);
        trx.setStatus(TrxStatus.PREPARE);
        while (true) {
            trx.setNo(generateRandomString(Long.toString(Instant.now().toEpochMilli())));
            try {
                log.info("prepare {}", trx.getNo());
                return repository.save(trx);
            } catch (Exception e) {
                log.info("duplicate {}", trx.getNo());
            }
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceTransaction prepare(TrxDeposit deposit) {
        ServiceTransaction trx = prepare(deposit.getAmount());
        trx.setStatus(TrxStatus.CDM_DEPOSIT);
        trx.setDeposit(deposit);
        trx.setBeneficiaryAccount(deposit.getBeneficiaryAccount());
        trx.setServiceProduct(deposit.getServiceProduct());
        trx= repository.save(trx);
        producerService.publishDataEvent(EventType.CREATE,trx);
        return trx;
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceTransaction prepare(ServiceTransaction trx) {
        log.info("Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        if (trx.getServiceProduct()==null && trx.getDeposit()!=null && trx.getDeposit().getServiceProduct()!=null) {
            trx.setServiceProduct(trx.getDeposit().getServiceProduct());
            trx= repository.save(trx);
            producerService.publishDataEvent(EventType.UPDATE,trx);
        }else{
            throw new ResourceNotFoundException("Service Product not found");
        }
        return trx;
    }
    public ServiceTransaction transaction(ServiceTransaction trx) {
        if(TrxStatus.SUCCESS.equals(trx.getStatus())){
            log.info("Transaction; id={}; amount={}; trx={}; Success", trx.getId(), trx.getAmount(), trx.getNo());
            return trx;
        }
        val product = trx.getServiceProduct();
        val productCode = product.getCode();
        if (productCode == null) {
            throw new ResourceNotFoundException("Service Product code not found");
        }
        try{
            if (ServiceRuleConfig.DEPOSIT.equals(product.getServiceRuleConfig())) {
                trx = serviceProductStoreTransfer(trx);
            }else {
                throw new ResourceNotFoundException("Service Product "+productCode+" support transaction");
            }
        }finally {
            producerService.publishDataEvent(EventType.UPDATE,trx);
        }
        return trx;
    }
    protected ServiceTransaction serviceProductStoreTransfer(ServiceTransaction trx) {
        val product = trx.getServiceProduct();
        log.info("Transfer Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        trx.setStatus(TrxStatus.TRANSACTION_IN_PROGRESS);
        TrxDepositPjpur depositPjpur = null;
        if (!PjpurRuleConfig.NONE.equals(product.getPjpurRuleConfig())) {
            depositPjpur =trx.getDepositPjpur();
            if (depositPjpur == null) {
                depositPjpur = pjpurService.prepare(trx.getDeposit());
                trx.setDepositPjpur(depositPjpur);
                repository.saveAndFlush(trx);
            }
            if (!TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                depositPjpur = pjpurService.deposit(trx.getDepositPjpur());
            }
            if (PjpurRuleConfig.MANDATORY_SUCCESS.equals(product.getPjpurRuleConfig()) && !TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                log.info("Transaction; id={}; amount={}; trx={}; Pjpur mandatory success. Pjpur Status", trx.getId(), trx.getAmount(), trx.getNo(),depositPjpur.getStatus());
                return trx;
            }
        }
        TrxTransfer transfer = null;
        if(!TransferRuleConfig.NONE.equals(product.getTransferRuleConfig())){
            transfer = trx.getTransfer();
            if (transfer == null) {
                transfer = transferService.prepare(trx);
                trx.setTransfer(transfer);
                repository.saveAndFlush(trx);
            }
            transferService.transferFund(transfer);
            if (TransferRuleConfig.MANDATORY_SUCCESS.equals(product.getPjpurRuleConfig()) && !TrxTransferStatus.SUCCESS.equals(transfer.getStatus())) {
                log.info("Transaction; id={}; amount={}; trx={}; Pjpur mandatory success. Pjpur Status", trx.getId(), trx.getAmount(), trx.getNo(),depositPjpur.getStatus());
                return trx;
            }
        }

        if (transfer != null && depositPjpur != null) {
            if (TrxTransferStatus.SUCCESS.equals(transfer.getStatus()) && TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                trx.setStatus(TrxStatus.SUCCESS);
            }
        }else if (transfer != null) {
            if (TrxTransferStatus.SUCCESS.equals(transfer.getStatus())) {
                trx.setStatus(TrxStatus.SUCCESS);
            }
        }
        return repository.saveAndFlush(trx);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, dontRollbackOn = Propagation5xxException.class)
    @Deprecated
    public ServiceTransaction executeTransaction(ServiceTransaction trx) {
        log.info("Transfer Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        var transfer = trx.getTransfer();
        if (transfer == null) {
            transfer = transferService.prepare(trx);
            trx.setTransfer(transfer);
            trx.setStatus(TrxStatus.TRANSFER);
            repository.save(trx);
        }
        trx.setStatus(TrxStatus.TRANSFER);
        repository.save(trx);
        try {
            transfer = transferService.transferFund(transfer);
            // Usage
            trx.setStatus(determineTransactionStatus(transfer.getStatus(),trx.getStatus()));
            trx = repository.save(trx);
        } catch (Propagation5xxException e) {
            trx.setStatus(TrxStatus.TRANSFER_TIME_OUT);
            repository.save(trx);
            throw e;
        }finally {
            producerService.publishDataEvent(EventType.UPDATE,trx);
        }
        return trx;
    }
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, dontRollbackOn = Propagation5xxException.class)
    public ServiceTransaction transfer(ServiceTransaction trx) {
        log.info("Transfer Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        var transfer = trx.getTransfer();
        if (transfer == null) {
            transfer = transferService.prepare(trx);
            trx.setTransfer(transfer);
            trx.setStatus(TrxStatus.TRANSFER);
            repository.save(trx);
        }
        trx.setStatus(TrxStatus.TRANSFER);
        repository.save(trx);
        try {
            transfer = transferService.transferFund(transfer);
            // Usage
            trx.setStatus(determineTransactionStatus(transfer.getStatus(),trx.getStatus()));
            trx = repository.save(trx);
        } catch (Propagation5xxException e) {
            trx.setStatus(TrxStatus.TRANSFER_TIME_OUT);
            repository.save(trx);
            throw e;
        }finally {
            producerService.publishDataEvent(EventType.UPDATE,trx);
        }
        return trx;
    }

    public ServiceTransaction retryTransfer(String trxNo) {
        ServiceTransaction trx = repository.findByNo(trxNo)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        log.info("Transfer Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        var transfer = trx.getTransfer();
        if (transfer == null) {
            transfer = transferService.prepare(trx);
            trx.setTransfer(transfer);
            trx.setStatus(TrxStatus.TRANSFER);
            repository.save(trx);
        }
        try {
            transfer = transferService.transferFund(transfer);
            // Usage
            trx.setStatus(determineTransactionStatus(transfer.getStatus(),trx.getStatus()));
            trx = repository.save(trx);
        } catch (Propagation5xxException e) {
            trx.setStatus(TrxStatus.TRANSFER_TIME_OUT);
            repository.save(trx);
            throw e;
        }finally {
            producerService.publishDataEvent(EventType.UPDATE,trx);
        }
        return trx;
    }

//    @KafkaListener(topics = "gateway-callback-topic", groupId = "cdt-core-transaction-customer-gateway-callback")
//    @Transactional()
//    public void consumeGatewayCallbackDTO(GatewayCallbackDTO event) {
//        log.info("consumeGatewayCallbackDTO: {}", event);
//        String gatewayCode = event.getGatewayCode();
//        String transactionNo = event.getTransactionNo();
//        String status = event.getStatus();
//        String message = event.getMessage();
//        Map<String, Object> dataMap=null;
//        if (event.getData() instanceof Map) {
//            dataMap = (Map<String, Object>) event.getData();
//            if (transactionNo == null) {
//                transactionNo = (String) dataMap.get("transaction_no");
//            }
//            if(status== null){
//                status = (String) dataMap.get("status");
//            }
//            if (message==null){
//                message = (String) dataMap.get("message");
//            }
//        }
//
//        log.info("gatewayCode: {} ,transactionNo: {} ,status: {} ,message: {}", gatewayCode,transactionNo,status,message);
//
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
//    }
}