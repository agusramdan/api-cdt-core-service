package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.service.accounttype.AccountTypeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/account-type/query")
@Tag(name = "AccountType Query API", description = "APIs for query AccountType")
@RequiredArgsConstructor
@Getter
public class AccountTypeQueryController implements
        BaseQueryController<AccountTypeQueryDTO, String> {
    private final AccountTypeQueryService service;

}
