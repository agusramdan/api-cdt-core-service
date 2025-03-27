package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import org.springframework.stereotype.Service;

@Service

public interface GatewayService {
    TrxTransfer setupGateway(TrxTransfer trx) ;
    TrxTransfer transferFund(TrxTransfer trx) ;
}
