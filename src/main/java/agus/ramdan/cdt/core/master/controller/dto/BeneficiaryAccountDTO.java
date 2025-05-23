package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.TID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountDTO", description = "DTO for querying beneficiary account details")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BeneficiaryAccountDTO implements TID<String> {
    @Schema(description = "Beneficiary Account ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;
    @JsonProperty("account_number")
    private String accountNumber;
    @Schema(description = "Account holder name", example = "John Doe")
    @JsonProperty("account_name")
    private String accountName;
    private BankDTO bank;
    private BranchDTO branch;
    private CustomerDTO customer;
}
