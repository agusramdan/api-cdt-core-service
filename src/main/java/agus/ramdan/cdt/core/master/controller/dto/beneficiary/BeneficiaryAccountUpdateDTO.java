package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "BeneficiaryAccountUpdateDTO", description = "DTO for updating an existing beneficiary account")
public class BeneficiaryAccountUpdateDTO {

    @Schema(description = "Beneficiary Account ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Bank account number (optional)", example = "1234567890")
    private String account_number;

    @Schema(description = "Account holder name (optional)", example = "John Doe")
    private String account_name;

    @Schema(description = "Bank code (optional)", example = "BCA")
    private String bank_code;

    @Schema(description = "Bank name (optional)", example = "Bank Central Asia")
    private String bank_name;
}
