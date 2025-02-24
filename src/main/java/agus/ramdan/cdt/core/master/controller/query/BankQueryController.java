package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.service.bank.BankQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/bank/query")
@Tag(name = "Bank Query API", description = "APIs for query Bank")
@RequiredArgsConstructor
@Getter
public class BankQueryController implements
        BaseQueryController<BankQueryDTO, String> {
    private final BankQueryService service;
}
