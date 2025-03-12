package agus.ramdan.cdt.core.trx.controller.client;

import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feign-client.api-cdt-core-trx-command-service.trx-pickup", url = "${feign-client.api-cdt-core-trx-command-service.base-url}/api/cdt/core/trx/pickup/command")
public interface TrxPickupCommandClient {

    @PostMapping
    TrxPickupQueryDTO create(@RequestBody TrxPickupCreateDTO dto);

}
