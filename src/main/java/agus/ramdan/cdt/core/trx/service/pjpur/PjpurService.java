package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.cdt.core.config.PjpurConfig;
import agus.ramdan.cdt.core.pjpur.controller.client.collect.PjpurCollectClient;
import agus.ramdan.cdt.core.pjpur.controller.client.deposit.PjpurDepositClient;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpur;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpurStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositPjpurRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class PjpurService  {
    private final PjpurConfig pjpurConfig;
    private final PjpurMapper pjpurMapper;
    private final TrxDepositPjpurRepository repository;
    private final PjpurCollectClient pjpurCollectClient;
    private final PjpurDepositClient pjpurDepositClient;
    private final TrxDataEventProducerService producerService;

    public TrxDepositPjpur prepare(TrxDeposit trx) {
        val pjpur = pjpurMapper.mapDepositPjpur(trx);
        pjpur.setBeneficiaryAccount(trx.getBeneficiaryAccount());
        if (pjpurConfig.isOnline()){
            pjpur.setStatus(TrxDepositPjpurStatus.PREPARE);
        }else {
            pjpur.setStatus(TrxDepositPjpurStatus.SUCCESS);
        }
        if(trx.getPjpurStatus()!=null) {
            pjpur.setStatus(trx.getPjpurStatus());
        }
        pjpur.setStatus(TrxDepositPjpurStatus.PREPARE);
        pjpur.setServiceTransaction(trx.getServiceTransaction());
        pjpur.setAmount(trx.getAmount());
        repository.save(pjpur);
        producerService.publishDataEvent(EventType.CREATE, pjpur);
        return pjpur;
    }

    public TrxDepositPjpur deposit(TrxDepositPjpur trx) {
        if (TrxDepositPjpurStatus.SUCCESS.equals(trx.getStatus())) {
            return trx;
        }
        if (!pjpurConfig.isOnline()) {
            trx.setStatus(TrxDepositPjpurStatus.SUCCESS);
            return repository.save(trx);
        }
        try{
            val result = pjpurDepositClient.deposit(pjpurMapper.mapDepositDTO(trx));
            log.info("Deposit result: {}", result);
            trx.setStatus(TrxDepositPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setStatus(TrxDepositPjpurStatus.FAIL);
            log.error("Error deposit: {}", e.getMessage());
        }finally {
            repository.save(trx);
        }
        return trx;
    }

    @Deprecated
    public TrxDeposit deposit(TrxDeposit trx) {
        try{
            val result = pjpurDepositClient.deposit(pjpurMapper.mapDepositDTO(trx));
            log.info("Deposit result: {}", result);
            trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setPjpurStatus(TrxDepositPjpurStatus.FAIL);
            log.error("Error deposit: {}", e.getMessage());
        }
        return trx;
    }
    @Transactional(Transactional.TxType.SUPPORTS)
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
