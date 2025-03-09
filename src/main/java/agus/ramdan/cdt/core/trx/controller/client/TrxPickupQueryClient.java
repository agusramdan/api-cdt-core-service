package agus.ramdan.cdt.core.trx.controller.client;

import agus.ramdan.base.client.BaseQueryClient;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "feign-client.api-cdm-core-trx-query-service.pickup", url = "${feign-client.api-cdm-core-trx-query-service.base-url}/api/cdt/core/trx/pickup/query")
public interface TrxPickupQueryClient extends BaseQueryClient<TrxPickupQueryDTO,String> {

}
