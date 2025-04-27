package agus.ramdan.cdt.core.pjpur.controller.client.collect;

import agus.ramdan.cdt.core.pjpur.controller.dto.ResponseDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.collect.CollectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "feign-client.api-cdt-pjpur-service.collect",
        url = "${feign-client.api-cdt-pjpur-service.base-url}/api/cdt/core/pjpur/collect/command"
)
public interface PjpurCollectClient {
    @PostMapping
    ResponseDTO collect(CollectDTO collectDTO);
}
