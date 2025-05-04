package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.base.dto.DepositCheckStatusDTO;
import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.dto.TransactionCheckStatusDTO;
import agus.ramdan.base.exception.PropagationXxxException;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.controller.dto.PjpurRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.TransferRuleConfig;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import agus.ramdan.cdt.core.trx.persistence.domain.*;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.pjpur.PjpurService;
import agus.ramdan.cdt.core.trx.service.transfer.TrxTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionService {

    private final ServiceTransactionRepository repository;
    private final TrxTransferService transferService;
    private final TrxDataEventProducerService producerService;
    private final PjpurService pjpurService;
    private final KafkaTemplate<String, DepositCheckStatusDTO> kafkaTemplate;
    private static final String CHARACTERS = "0123456789";
    private static final int STRING_LENGTH = 20;
    private static final Random random = new Random();

    public ServiceTransaction findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }
    public String generateRandomString(String input) {
        StringBuilder result = new StringBuilder(STRING_LENGTH);
        result.append(input);
        for (int i = input.length(); i < STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }
//    private TrxStatus determineTransactionStatus(TrxTransferStatus transferStatus, TrxStatus defaultStatus) {
//        return switch (transferStatus) {
//            case SUCCESS -> TrxStatus.TRANSFER_SUCCESS;
//            case REVERSAL -> TrxStatus.TRANSFER_REVERSAL;
//            case FAILED -> TrxStatus.TRANSFER_FAILED;
//            default -> defaultStatus;
//        };
//    }
    @Transactional
    public ServiceTransaction prepare(BigDecimal amount, ServiceProduct product) {
        val trx = new ServiceTransaction();
        trx.setAmount(amount);
        trx.setStatus(TrxStatus.PREPARE);
        trx.setServiceProduct(product);
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
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public ServiceTransaction prepare(TrxDeposit deposit) {
        ServiceTransaction trx = prepare(deposit.getAmount(), deposit.getServiceProduct());
        trx.setStatus(TrxStatus.CDM_DEPOSIT);
        trx.setDeposit(deposit);
        trx.setBeneficiaryAccount(deposit.getBeneficiaryAccount());
        trx= repository.saveAndFlush(trx);
        producerService.publishDataEvent(EventType.CREATE,trx);
        return trx;
    }
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public ServiceTransaction prepare(ServiceTransaction trx) {
        var product = checkServiceProduct(trx);
        try {
            if (!PjpurRuleConfig.isNONE(product.getPjpurRuleConfig()) && trx.getDepositPjpur() == null) {
                trx.setDepositPjpur(pjpurService.prepare(trx.getDeposit()));
            }
            if(!TransferRuleConfig.isNONE(product.getTransferRuleConfig()) && trx.getTransfer() == null){
                trx.setTransfer(transferService.prepare(trx));
            }
            trx= repository.saveAndFlush(trx);
            producerService.publishDataEvent(EventType.UPDATE,trx);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        checkStatusTransaction(trx);
        return trx;
    }

    protected ServiceProduct checkServiceProduct(ServiceTransaction trx) {
        var product = trx.getServiceProduct();
        if (product == null) {
            if (trx.getDeposit() != null && trx.getDeposit().getServiceProduct() != null) {
                product = trx.getDeposit().getServiceProduct();
                log.info("Transaction Product; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
                trx.setServiceProduct(product);
            } else {
                trx.setStatus(TrxStatus.REJECT);
                trx = repository.saveAndFlush(trx);
                producerService.publishDataEvent(EventType.UPDATE, trx);
                log.error("Service Product not found {} ,{}", trx.getId(), trx.getNo());
                throw new ResourceNotFoundException("Service Product not found");
            }
            return product;
        }
        return product;
    }
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public ServiceTransaction transaction(ServiceTransaction trx) {
        if(TrxStatus.SUCCESS.equals(trx.getStatus())){
            log.info("Transaction; id={}; amount={}; trx={}; Success", trx.getId(), trx.getAmount(), trx.getNo());
            return trx;
        }
        trx.setStatus(TrxStatus.TRANSACTION_IN_PROGRESS);
        log.info("Start Transaction; id={}; amount={}; trx={}; finish", trx.getId(), trx.getAmount(), trx.getNo());
        val product = checkServiceProduct(trx);
        if (!ServiceRuleConfig.DEPOSIT.equals(product.getServiceRuleConfig())){
            trx.setStatus(TrxStatus.REJECT);
            trx= repository.saveAndFlush(trx);
            producerService.publishDataEvent(EventType.UPDATE,trx);
            throw new ResourceNotFoundException("Service Product "+product.getCode()+" not support transaction");
        }
        try {
            trx = serviceProductStoreTransfer(trx);
        }catch (Exception ex) {
                log.error("Error transaction:", ex);
        }finally {
            try {
                trx = repository.saveAndFlush(trx);
                producerService.publishDataEvent(EventType.UPDATE,trx);
                log.info("Finish Transaction; id={}; amount={}; trx={}", trx.getId(), trx.getAmount(), trx.getNo());
            } catch (Exception e) {
                log.error("Error transaction:", e);
            }
        }
        return trx;
    }
    protected ServiceTransaction serviceProductStoreTransfer(ServiceTransaction trx) {
        val product = trx.getServiceProduct();
        log.info("Transfer Transaction; id={}; amount={}; trx={};", trx.getId(), trx.getAmount(), trx.getNo());
        trx.setStatus(TrxStatus.TRANSACTION_IN_PROGRESS);
        TrxDepositPjpur depositPjpur = null;
        if (!PjpurRuleConfig.isNONE(product.getPjpurRuleConfig()) && trx.getDepositPjpur() != null) {
            depositPjpur =trx.getDepositPjpur();
            if (!TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                depositPjpur = pjpurService.deposit(depositPjpur);
            }
            if (PjpurRuleConfig.MANDATORY_SUCCESS.equals(product.getPjpurRuleConfig()) && !TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                trx.setStatus(TrxStatus.PJPUR_FAILED);
                log.info("Transaction; id={}; amount={}; trx={}; Pjpur mandatory success", trx.getId(), trx.getAmount(), trx.getNo(),depositPjpur.getStatus());
                return trx;
            }
        }
        TrxTransfer transfer = null;
        if(!TransferRuleConfig.isNONE(product.getTransferRuleConfig()) && trx.getTransfer() != null){
            transfer = trx.getTransfer();
            if(!TrxTransferStatus.SUCCESS.equals(transfer.getStatus())) {
                transfer = transferService.transferFund(trx.getTransfer());
            }
            trx.setTransfer(transfer);
            if (TransferRuleConfig.MANDATORY_SUCCESS.equals(product.getTransferRuleConfig()) && !TrxTransferStatus.SUCCESS.equals(transfer.getStatus())) {
                trx.setStatus(TrxStatus.TRANSFER_FAILED);
                log.info("Transaction; id={}; amount={}; trx={}; mandatory success. Status", trx.getId(), trx.getAmount(), trx.getNo(),transfer.getStatus());
                return trx;
            }
        }

        return checkStatusTransaction(trx);
    }

    protected ServiceTransaction checkStatusTransaction(ServiceTransaction trx) {
        val status = trx.getStatus();
        val product = trx.getServiceProduct();
        try {
            if (product == null) {
                trx.setStatus(TrxStatus.REJECT);
                log.error("Service Product not found {} ,{}", trx.getId(), trx.getNo());
                throw new ResourceNotFoundException("Service Product code not found");
            }
            boolean success = true;
            if (!PjpurRuleConfig.isNONE(product.getPjpurRuleConfig()) ) {
                val depositPjpur = trx.getDepositPjpur();
                if (depositPjpur == null || !TrxDepositPjpurStatus.SUCCESS.equals(depositPjpur.getStatus())) {
                    success = false;
                }
            }
            if (!TransferRuleConfig.isNONE(product.getTransferRuleConfig()) ) {
                val transfer = trx.getTransfer();
                if (transfer == null || !TrxTransferStatus.SUCCESS.equals(transfer.getStatus())) {
                    success = false;
                }
            }
            if(success){
                if(!TrxStatus.SUCCESS.equals(trx.getStatus())){
                    trx.setStatus(TrxStatus.SUCCESS);
                }
            }else {
                if(TrxStatus.SUCCESS.equals(trx.getStatus())){
                    trx.setStatus(TrxStatus.TRANSACTION_IN_PROGRESS);
                }
            }
        }finally {
            if (trx.getStatus() != status) {
                try {
                    trx = repository.saveAndFlush(trx);
                    producerService.publishDataEvent(EventType.UPDATE, trx);
                    log.info("Finish check Transaction; id={}; amount={}; trx={}", trx.getId(), trx.getAmount(), trx.getNo());
                } catch (Exception e) {
                    log.error("Error check transaction:", e);
                }
            }
        }
        return trx;
    }
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public void transactionReTray(UUID trxNo) {
        try {
            repository.findById(trxNo).map(this::prepare).map(this::transaction);
        }catch (Exception e) {
            log.error("Error transactionReTray: {}",trxNo);
        }

    }

    @KafkaListener(topics = "core-trx-status-check-event", groupId = "cdt-core-transaction-callback")
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public void transactionCallback(TransactionCheckStatusDTO trxNo) {
        val trx = repository.findById(trxNo.getId());
        ServiceTransaction sc = null;
        if (trx.isPresent()) {
            try {
                sc = checkStatusTransaction(prepare(trx.get()));
            } catch (Exception e) {
                log.error("Transaction Exception", e);
                return;
            }
            val result = transaction(sc);
            if (result.getDeposit()!= null) {
                val deposit = new DepositCheckStatusDTO();
                deposit.setId(result.getDeposit().getId());
                deposit.setSource("ServiceTransaction");
                deposit.setMessage(trxNo.getMessage());
                kafkaTemplate.send("core-deposit-status-check-event",deposit);
            }
        } else {
            log.error("Transaction not found; id={}", trxNo.getId());
        }
    }
}