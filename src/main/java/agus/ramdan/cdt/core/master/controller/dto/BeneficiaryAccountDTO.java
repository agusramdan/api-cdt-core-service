package agus.ramdan.cdt.core.master.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountQueryDTO", description = "DTO for querying beneficiary account details")
public class BeneficiaryAccountDTO {

    @Schema(description = "Beneficiary Account ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Bank account number", example = "1234567890")
    private String account_number;

    @Schema(description = "Account holder name", example = "John Doe")
    private String account_name;
    private BankDTO bank;

}
