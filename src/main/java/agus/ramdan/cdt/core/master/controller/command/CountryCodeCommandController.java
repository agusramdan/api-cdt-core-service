package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeUpdateDTO;
import agus.ramdan.cdt.core.master.service.countrycode.CountryCodeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/country-code/command")
@RequiredArgsConstructor
public class CountryCodeCommandController implements
        BaseCommandController<CountryCodeQueryDTO, CountryCodeCreateDTO, CountryCodeUpdateDTO, String> {

    private final CountryCodeCommandService service;

    @Override
    public CountryCodeCommandService getService() {
        return service;
    }
}
