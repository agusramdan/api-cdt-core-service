package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customertype.CustomerTypeUpdateDTO;
import agus.ramdan.cdt.core.master.service.customertype.CustomerTypeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-type/command")
@RequiredArgsConstructor
public class CustomerTypeCommandController implements
        BaseCommandController<CustomerTypeQueryDTO, CustomerTypeCreateDTO, CustomerTypeUpdateDTO, String> {

    private final CustomerTypeCommandService service;

    @Override
    public CustomerTypeCommandService getService() {
        return service;
    }
}

