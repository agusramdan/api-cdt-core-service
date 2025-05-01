package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpurStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Profile("!pjpur")
public class PjpurServiceOffline implements PjpurService {
    @Override
    public TrxDeposit deposit(TrxDeposit trx) {
        trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
        return trx;
    }
    @Override
    public TrxPickup collect(TrxPickup trx) {
        trx.setPjpurStatus(TrxDepositPjpurStatus.SUCCESS);
        return trx;
    }
}

