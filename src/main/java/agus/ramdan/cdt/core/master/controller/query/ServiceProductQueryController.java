package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.service.product.ServiceProductQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/service-product/query")
@Tag(name = "Service Product API", description = "APIs for query Service Product")
@RequiredArgsConstructor
@Getter
public class ServiceProductQueryController implements BaseQueryController<ServiceProductQueryDTO, String> {
    private final ServiceProductQueryService service;
}
