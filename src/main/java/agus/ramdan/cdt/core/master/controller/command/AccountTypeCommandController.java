package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeUpdateDTO;
import agus.ramdan.cdt.core.master.service.accounttype.AccountTypeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/account-type/command")
@RequiredArgsConstructor
public class AccountTypeCommandController implements
        BaseCommandController<AccountTypeQueryDTO, AccountTypeCreateDTO, AccountTypeUpdateDTO, String> {

    private final AccountTypeCommandService service;

    @Override
    public AccountTypeCommandService getService() {
        return service;
    }
}

