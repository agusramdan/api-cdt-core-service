package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayUpdateDTO;
import agus.ramdan.cdt.core.master.service.gateway.GatewayCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/gateway/command")
@Tag(name = "Gateway Command API", description = "APIs for creating, updating, and deleting gateway")
@RequiredArgsConstructor
@Getter
public class GatewayCommandController implements
        BaseCommandController<GatewayQueryDTO, GatewayCreateDTO, GatewayUpdateDTO, String> {
    private final GatewayCommandService service;
}