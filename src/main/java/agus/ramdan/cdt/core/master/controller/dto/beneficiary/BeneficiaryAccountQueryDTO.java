package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountQueryDTO", description = "DTO for querying beneficiary account details")
public class BeneficiaryAccountQueryDTO {

    @Schema(description = "Beneficiary Account ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Customer ID associated with this account", example = "550e8400-e29b-41d4-a716-446655440000")
    @JsonProperty("customer_id")
    private String customerId;

    @Schema(description = "Bank account number", example = "1234567890")
    private String account_number;

    @Schema(description = "Account holder name", example = "John Doe")
    private String account_name;
    private BankDTO bank;

    private BranchDTO branch;

//    @Schema(description = "Bank code", example = "BCA")
//    private String bank_code;
//
//    @Schema(description = "Bank name", example = "Bank Central Asia")
//    private String bank_name;
}
