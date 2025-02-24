package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.location.ServiceLocationQueryDTO;
import agus.ramdan.cdt.core.master.service.location.ServiceLocationQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/service-location/query")
@Tag(name = "Service Location Query API", description = "APIs for query Service Location")
@RequiredArgsConstructor
@Getter
public class ServiceLocationQueryController implements
        BaseQueryController<ServiceLocationQueryDTO, String> {

    private final ServiceLocationQueryService service;

}
