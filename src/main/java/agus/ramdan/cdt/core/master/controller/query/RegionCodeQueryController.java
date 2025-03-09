package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeQueryDTO;
import agus.ramdan.cdt.core.master.service.regioncode.RegionCodeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/region-code/query")
@Tag(name = "RegionCode Query API", description = "APIs for query RegionCode")
@RequiredArgsConstructor
@Getter
public class RegionCodeQueryController implements
        BaseQueryController<RegionCodeQueryDTO, String> {
    private final RegionCodeQueryService service;

}
