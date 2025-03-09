package agus.ramdan.cdt.core.trx.controller.client;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feign-client.api-cdm-core-trx-command-service.deposit", url = "${feign-client.api-cdm-core-trx-command-service.base-url}/api/cdt/core/trx/deposit/command")
public interface TrxDepositCommandClient {

    @PostMapping
    TrxDepositQueryDTO create(@RequestBody TrxDepositCreateDTO dto);

    @PutMapping
    TrxDepositQueryDTO update(@RequestBody TrxDepositUpdateDTO dto);

}
