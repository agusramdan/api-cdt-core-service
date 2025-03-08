package agus.ramdan.cdt.core.gateway.controller.client.transfer;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "feign-client.api-cdt-gateway-service.transfer-balance", url = "${feign-client.api-cdt-gateway-service.base-url}/api/cdt/core/gateway/transfer/command")
public interface TransferBalanceClient {
    TransferBalanceResponseDTO transferBalance(TransferBalanceRequestDTO transferBalanceRequestDTO);
}
