package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorUpdateDTO;
import agus.ramdan.cdt.core.master.service.vendor.VendorCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/vendor/command")
@Tag(name = "Vendor Command API", description = "APIs for creating, updating, and deleting Vendor")
@RequiredArgsConstructor
@Getter
public class VendorCommandController implements
        BaseCommandController<VendorQueryDTO, VendorCreateDTO, VendorUpdateDTO, String> {

    private final VendorCommandService service;
}