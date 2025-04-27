package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import org.springframework.stereotype.Service;

@Service

public interface PjpurService {
    TrxDeposit deposit(TrxDeposit trx) ;
    TrxPickup collect(TrxPickup trx) ;
}
