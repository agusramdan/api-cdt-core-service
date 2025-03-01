package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GatewayService {

    public ServiceTransaction setupGateway(ServiceTransaction trx){
        return trx;
    }
    public ServiceTransaction transferFund(ServiceTransaction trx) {
        //trx.setGateway();
        return trx;
    }

}

