package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchUpdateDTO;
import agus.ramdan.cdt.core.master.service.branch.BranchCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/branch/command")
@Tag(name = "Branch Command API", description = "APIs for creating, updating, and deleting Branch")
@RequiredArgsConstructor
@Getter
public class BranchCommandController implements BaseCommandController<BranchQueryDTO, BranchCreateDTO, BranchUpdateDTO, String> {
    private final BranchCommandService service;

}
