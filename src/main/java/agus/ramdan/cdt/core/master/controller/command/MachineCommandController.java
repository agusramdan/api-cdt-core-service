package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineUpdateDTO;
import agus.ramdan.cdt.core.master.service.machine.MachineCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/machine/command")
@RequiredArgsConstructor
@Tag(name = "Machine Command API", description = "APIs for creating, updating, and deleting machine")
@Getter
public class MachineCommandController implements
        BaseCommandController<MachineQueryDTO, MachineCreateDTO, MachineUpdateDTO, String> {

    private final MachineCommandService service;
}
