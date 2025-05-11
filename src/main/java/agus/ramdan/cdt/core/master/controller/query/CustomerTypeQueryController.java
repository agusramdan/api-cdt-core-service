package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.service.customertype.CustomerTypeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-type/query")
@Tag(name = "CustomerType Query API", description = "APIs for query CustomerType")
@RequiredArgsConstructor
@Getter
public class CustomerTypeQueryController implements
        BaseQueryController<CustomerTypeQueryDTO, String> {
    private final CustomerTypeQueryService service;

}
