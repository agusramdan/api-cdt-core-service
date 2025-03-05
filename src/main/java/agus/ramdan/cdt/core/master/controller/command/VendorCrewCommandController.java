package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewUpdateDTO;
import agus.ramdan.cdt.core.master.service.vendorcrew.VendorCrewCommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/vendor-crew/command")
@RequiredArgsConstructor
@Getter
public class VendorCrewCommandController implements
        BaseCommandController<VendorCrewQueryDTO, VendorCrewCreateDTO, VendorCrewUpdateDTO, String> {

    private final VendorCrewCommandService service;

}
