package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/master/branch/query")
@Tag(name = "Branch Query API", description = "APIs for query Branch")
@RequiredArgsConstructor
@Getter
public class BranchQueryController implements BaseQueryController<BranchQueryDTO, String> {
    private final BranchQueryService service;
}

