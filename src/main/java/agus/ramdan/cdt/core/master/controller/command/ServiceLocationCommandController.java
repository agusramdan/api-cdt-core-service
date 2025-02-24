package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationUpdateDTO;
import agus.ramdan.cdt.core.master.service.location.ServiceLocationCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/service-location/command")
@Tag(name = "Service Location Command API", description = "APIs for creating, updating, and deleting service location")
@RequiredArgsConstructor
@Getter
public class ServiceLocationCommandController implements
        BaseCommandController<ServiceLocationQueryDTO, ServiceLocationCreateDTO, ServiceLocationUpdateDTO, String> {

    private final ServiceLocationCommandService service;
}