package agus.ramdan.cdt.core.pjpur.controller.client.deposit;

import agus.ramdan.cdt.core.pjpur.controller.dto.ResponseDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.deposit.DepositDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "feign-client.api-cdt-pjpur-service.deposit",
        url = "${feign-client.api-cdt-pjpur-service.base-url}/api/cdt/core/pjpur/deposit/command"
)
public interface PjpurDepositClient {
    @PostMapping
    ResponseDTO deposit(DepositDTO depositDTO);
}
