package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusQueryDTO;
import agus.ramdan.cdt.core.master.service.customerstatus.CustomerStatusQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-status/query")
@Tag(name = "CustomerStatus Query API", description = "APIs for query CustomerStatus")
@RequiredArgsConstructor
@Getter
public class CustomerStatusQueryController implements
        BaseQueryController<CustomerStatusQueryDTO, String> {
    private final CustomerStatusQueryService service;

}
