package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.service.beneficiary.BeneficiaryAccountQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/beneficiary-accounts/query")
@Tag(name = "Beneficiary Query Account API", description = "APIs for query Beneficiary Account")
@RequiredArgsConstructor
@Getter
public class BeneficiaryAccountQueryController implements BaseQueryController<BeneficiaryAccountQueryDTO, String> {
    private final BeneficiaryAccountQueryService service;
}
