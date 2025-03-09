package agus.ramdan.cdt.core.trx.controller.client;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feign-client.api-cdm-core-trx-query-service.deposit", url = "${feign-client.api-cdm-core-trx-query-service.base-url}/api/cdt/core/trx/deposit/query")
public interface TrxDepositQueryClient {
    @GetMapping("/{id}")
    TrxDepositQueryDTO getById(@PathVariable String id);
}
