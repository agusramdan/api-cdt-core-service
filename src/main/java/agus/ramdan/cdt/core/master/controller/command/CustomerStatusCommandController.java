package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customerstatus.CustomerStatusUpdateDTO;
import agus.ramdan.cdt.core.master.service.customerstatus.CustomerStatusCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-status/command")
@RequiredArgsConstructor
public class CustomerStatusCommandController implements
        BaseCommandController<CustomerStatusQueryDTO, CustomerStatusCreateDTO, CustomerStatusUpdateDTO, String> {

    private final CustomerStatusCommandService service;

    @Override
    public CustomerStatusCommandService getService() {
        return service;
    }
}

