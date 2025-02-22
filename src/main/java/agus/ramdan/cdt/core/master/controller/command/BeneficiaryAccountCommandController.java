package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountUpdateDTO;
import agus.ramdan.cdt.core.master.service.beneficiary.BeneficiaryAccountCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/cdt/core/master/beneficiary-accounts/command")
@Tag(name = "Beneficiary Account Command API", description = "APIs for creating, updating, and deleting beneficiary")
@RequiredArgsConstructor
public class BeneficiaryAccountCommandController implements BaseCommandController< BeneficiaryAccountQueryDTO,BeneficiaryAccountCreateDTO, BeneficiaryAccountUpdateDTO,UUID> {
    @Getter
    private final BeneficiaryAccountCommandService service;

}