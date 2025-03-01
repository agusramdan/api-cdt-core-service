package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountUpdateDTO", description = "DTO for updating an existing beneficiary account")
public class BeneficiaryAccountUpdateDTO {


    @Schema(description = "Bank account number (optional)", example = "1234567890")
    private String account_number;

    @Schema(description = "Account holder name (optional)", example = "John Doe")
    private String account_name;

    private BankDTO bank;

    private BranchDTO branch;

//    @Schema(description = "Bank code (optional)", example = "BCA")
//    private String bank_code;
//
//    @Schema(description = "Bank name (optional)", example = "Bank Central Asia")
//    private String bank_name;
}
