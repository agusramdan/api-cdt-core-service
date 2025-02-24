package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.branch.BranchQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.master.service.gateway.GatewayQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/master/gateway/query")
@Tag(name = "Gateway Query API", description = "APIs for query Gateway")
@RequiredArgsConstructor
@Getter
public class GatewayQueryController implements BaseQueryController<GatewayQueryDTO, String> {
    private final GatewayQueryService service;
}

