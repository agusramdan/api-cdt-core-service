package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.service.customercrew.CustomerCrewCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-crew/command")
@RequiredArgsConstructor
public class CustomerCrewCommandController implements
        BaseCommandController<CustomerCrewQueryDTO, CustomerCrewCreateDTO, CustomerCrewUpdateDTO, String> {

    private final CustomerCrewCommandService service;

    @Override
    public CustomerCrewCommandService getService() {
        return service;
    }
}
