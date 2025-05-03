package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.PropagationXxxException;
import agus.ramdan.cdt.core.config.PjpurConfig;
import agus.ramdan.cdt.core.pjpur.controller.client.collect.PjpurCollectClient;
import agus.ramdan.cdt.core.pjpur.controller.client.deposit.PjpurDepositClient;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpur;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpurStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositPjpurRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class PjpurService  {
    private final PjpurConfig pjpurConfig;
    private final PjpurMapper pjpurMapper;
    private final TrxDepositPjpurRepository repository;
    private final PjpurCollectClient pjpurCollectClient;
    private final PjpurDepositClient pjpurDepositClient;
    private final TrxDataEventProducerService producerService;
    public TrxDepositPjpur findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public TrxDepositPjpur prepare(TrxDeposit trx) {
        log.info("Prepare Trx Deposit PJPUR ; id={}; amount={}", trx.getId(), trx.getAmount());
        val pjpur = pjpurMapper.mapDepositPjpur(trx);
        if (pjpurConfig.isOnline()){
            if(trx.getPjpurStatus()!=null) {
                pjpur.setStatus(trx.getPjpurStatus());
            }else {
                pjpur.setStatus(TrxDepositPjpurStatus.PREPARE);
            }
        }else {
            pjpur.setStatus(TrxDepositPjpurStatus.SUCCESS);
        }
        repository.saveAndFlush(pjpur);
        producerService.publishDataEvent(EventType.CREATE, pjpur);
        return pjpur;
    }

    @Transactional(noRollbackFor = PropagationXxxException.class)
    public TrxDepositPjpur deposit(TrxDepositPjpur trx) {
        if (TrxDepositPjpurStatus.SUCCESS.equals(trx.getStatus())) {
            return trx;
        }
        if (!pjpurConfig.isOnline()) {
            trx.setStatus(TrxDepositPjpurStatus.SUCCESS);
            producerService.publishDataEvent(EventType.UPDATE, trx=repository.saveAndFlush(trx));
            return trx;
        }
        try{
            if (trx.getTrxDateTime() == null) {
                trx.setTrxDateTime(trx.getServiceTransaction().getTrxDate());
            }
            if (trx.getTrxDateTime() == null) {
                trx.setTrxDateTime(LocalDateTime.now());
            }
            val result = pjpurDepositClient.deposit(pjpurMapper.mapDepositDTO(trx));
            log.info("TrxDeposit Pjpur result: {}", result);
            trx.setStatus(TrxDepositPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setStatus(TrxDepositPjpurStatus.FAIL);
            log.error("Error TrxDeposit Pjpur: {}", e.getMessage());
        }finally {
            producerService.publishDataEvent(EventType.UPDATE, trx=repository.saveAndFlush(trx));
        }
        return trx;
    }

//    @Deprecated
//    public TrxDeposit deposit(TrxDeposit trx) {
//        try{
//            val result = pjpurDepositClient.deposit(pjpurMapper.mapDepositDTO(trx));
//            log.info("Deposit result: {}", result);
//            trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
//        } catch (Exception e) {
//            trx.setPjpurStatus(TrxDepositPjpurStatus.FAIL);
//            log.error("Error deposit: {}", e.getMessage());
//        }
//        return trx;
//    }
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public TrxPickup collect(TrxPickup trx) {
        if (!pjpurConfig.isOnline()) {
            trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
        }
        if (TrxDepositPjpurStatus.SUCCESS.equals(trx.getStatus())) {
            return trx;
        }
        try{
            val result = pjpurCollectClient.collect(pjpurMapper.mapCollectDTO(trx));
            trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setPjpurStatus(TrxDepositPjpurStatus.FAIL);
            log.error("Error collect: {}", e.getMessage());
        }
        return trx;
    }
}
