package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeQueryDTO;
import agus.ramdan.cdt.core.master.service.countrycode.CountryCodeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/country-code/query")
@Tag(name = "CountryCode Query API", description = "APIs for query CountryCode")
@RequiredArgsConstructor
@Getter
public class CountryCodeQueryController implements
        BaseQueryController<CountryCodeQueryDTO, String> {
    private final CountryCodeQueryService service;

}
