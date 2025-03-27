package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.core.config.PaymentGatewayConfig;
import agus.ramdan.cdt.core.master.service.gateway.GatewayQueryService;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Profile("pgoffline")
public class GatewayServiceOffline {
    private final GatewayQueryService gatewayQueryService;
    private final GatewayTransferMapper gatewayTransferMapper;
//    private final TransferBalanceClient transferBalanceClient;
    private final PaymentGatewayConfig paymentGatewayConfig;

    public TrxTransfer setupGateway(TrxTransfer trx) {
        val gatewayCode = paymentGatewayConfig.getCode();
        log.info("Gateway code {}",gatewayCode);
        val opt = gatewayQueryService.findByCode(gatewayCode);
        opt.ifPresentOrElse(trx::setGateway,()-> {throw new XxxException("Server Error Config Gateway Code",500);});
        return trx;
    }

    public TrxTransfer transferFund(TrxTransfer trx) {
        trx.setStatus(paymentGatewayConfig.getTransferStatus());
        return trx;
    }
}
