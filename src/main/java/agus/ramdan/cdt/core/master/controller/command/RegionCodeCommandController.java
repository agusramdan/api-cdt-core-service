package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeUpdateDTO;
import agus.ramdan.cdt.core.master.service.regioncode.RegionCodeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/region-code/command")
@RequiredArgsConstructor
public class RegionCodeCommandController implements
        BaseCommandController<RegionCodeQueryDTO, RegionCodeCreateDTO, RegionCodeUpdateDTO, String> {

    private final RegionCodeCommandService service;

    @Override
    public RegionCodeCommandService getService() {
        return service;
    }
}

