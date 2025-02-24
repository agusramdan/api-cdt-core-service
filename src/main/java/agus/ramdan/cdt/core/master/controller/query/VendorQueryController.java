package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorQueryDTO;
import agus.ramdan.cdt.core.master.service.bank.BankQueryService;
import agus.ramdan.cdt.core.master.service.vendor.VendorQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/vendor/query")
@Tag(name = "Vendor Query API", description = "APIs for query Vendor")
@RequiredArgsConstructor
@Getter
public class VendorQueryController implements
        BaseQueryController<VendorQueryDTO, String> {

    private final VendorQueryService service;

}
