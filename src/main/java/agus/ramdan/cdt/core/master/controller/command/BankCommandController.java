package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankUpdateDTO;
import agus.ramdan.cdt.core.master.service.bank.BankCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/bank/command")
@Tag(name = "Bank Command API", description = "APIs for creating, updating, and deleting bank")
@RequiredArgsConstructor
public class BankCommandController implements
        BaseCommandController<BankQueryDTO, BankCreateDTO, BankUpdateDTO, String> {

    private final BankCommandService service;

    @Override
    public BankCommandService getService() {
        return service;
    }
}
