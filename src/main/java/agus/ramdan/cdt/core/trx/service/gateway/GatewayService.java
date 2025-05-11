package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import org.springframework.stereotype.Service;

@Service

public interface GatewayService {
    TrxTransfer setupGateway(TrxTransfer trx) ;
    TrxTransfer transferFund(TrxTransfer trx) ;
    TrxTransferStatus mapGatewayStatus(String status);
}
