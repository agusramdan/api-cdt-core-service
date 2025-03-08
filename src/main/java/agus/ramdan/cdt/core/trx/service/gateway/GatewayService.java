package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GatewayService {

    public TrxTransfer setupGateway(TrxTransfer trx){
        return trx;
    }
    public TrxTransfer transferFund(TrxTransfer trx) {
        //trx.setGateway();
        return trx;
    }

}

