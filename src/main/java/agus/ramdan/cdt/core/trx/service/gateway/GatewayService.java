package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.cdt.core.gateway.controller.client.transfer.TransferBalanceClient;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
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

    public TrxTransfer setupGateway(TrxTransfer trx) {
        return trx;
    }

    public TrxTransfer transferFund(TrxTransfer trx) {
        val dto = gatewayTransferMapper.mapDTO(trx);

        val response = transferBalanceClient.transferBalance(dto);
        /**
         * List of status transaction:
         * 1 = On Process
         * 2 = Success
         * 4 = Failed
         * 5 = Reverse
         */
        if ("2".equals(response.getStatus())) {
            trx.setStatus(TrxTransferStatus.SUCCESS);
        } else if ("4".equals(response.getStatus())) {
            trx.setStatus(TrxTransferStatus.FAILED);
        } else if ("5".equals(response.getStatus())) {
            trx.setStatus(TrxTransferStatus.REVERSAL);
        }
        //trx.setGateway();
        return trx;
    }

}

