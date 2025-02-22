package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.service.customer.CustomerQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/master/customers/query")
@Tag(name = "Customer API", description = "APIs for query Customer")
@RequiredArgsConstructor
@Getter
public class CustomerQueryController implements BaseQueryController<CustomerQueryDTO, UUID> {
    private final CustomerQueryService service;
}

