package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/machine/query")
@Tag(name = "Machine Query Account API", description = "APIs for query Machine")
@RequiredArgsConstructor
@Getter
public class MachineQueryController implements
        BaseQueryController<MachineQueryDTO, String> {
    private final MachineQueryService service;
}

