package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema(name = "BeneficiaryAccountCreateDTO", description = "DTO for creating a new beneficiary account")
@Validated
public class BeneficiaryAccountCreateDTO {

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format for customer_id")
    @Schema(description = "Customer ID associated with this account", example = "550e8400-e29b-41d4-a716-446655440000")
    @JsonProperty(value = "customer_id",required = true)
    private String customerId;

    @NotNull(message = " account_number can't null.")
    @NotBlank(message = " account_number can't blank.")
    @Schema(description = "Bank account number", example = "1234567890")
    private String account_number;

    @NotBlank
    @Schema(description = "Account holder name", example = "John Doe")
    private String account_name;
    @NotNull(message = " bank can't null.")
    private BankDTO bank;

    private BranchDTO branch;

//    @NotBlank
//    @Schema(description = "Bank code", example = "BCA")
//    private String bank_code;
//
//    @NotBlank
//    @Schema(description = "Bank name", example = "Bank Central Asia")
//    private String bank_name;
}
