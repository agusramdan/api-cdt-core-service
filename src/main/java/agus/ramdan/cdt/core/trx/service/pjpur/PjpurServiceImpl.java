package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.core.pjpur.controller.client.collect.PjpurCollectClient;
import agus.ramdan.cdt.core.pjpur.controller.client.deposit.PjpurDepositClient;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPjpurStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Profile("pjpur")
public class PjpurServiceImpl implements PjpurService {
    private final PjpurMapper pjpurMapper;
    private final PjpurCollectClient pjpurCollectClient;
    private final PjpurDepositClient pjpurDepositClient;

    @Override
    public TrxDeposit deposit(TrxDeposit trx) {
        try{
            val result = pjpurDepositClient.deposit(pjpurMapper.mapDepositDTO(trx));
            trx.setPjpurStatus(TrxPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setPjpurStatus(TrxPjpurStatus.FAIL);
            log.error("Error deposit: {}", e.getMessage());
        }
        return trx;
    }

    @Override
    public TrxPickup collect(TrxPickup trx) {
        try{
            val result = pjpurCollectClient.collect(pjpurMapper.mapCollectDTO(trx));
            trx.setPjpurStatus(TrxPjpurStatus.SUCCESS);
        } catch (Exception e) {
            trx.setPjpurStatus(TrxPjpurStatus.FAIL);
            log.error("Error collect: {}", e.getMessage());
        }
        return trx;
    }
}
