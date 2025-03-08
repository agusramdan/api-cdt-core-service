package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.cdt.core.gateway.controller.client.transfer.TransferBalanceClient;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GatewayService {
    private final GatewayTransferMapper gatewayTransferMapper;
    private final TransferBalanceClient transferBalanceClient;

    public TrxTransfer setupGateway(TrxTransfer trx){
        return trx;
    }
    public TrxTransfer transferFund(TrxTransfer trx) {
        val dto = gatewayTransferMapper.mapDTO(trx);

        val response = transferBalanceClient.transferBalance(dto);
        //trx.setGateway();
        return trx;
    }

}

