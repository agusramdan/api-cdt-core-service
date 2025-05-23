package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.*;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountQueryDTO", description = "DTO for querying beneficiary account details")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BeneficiaryAccountQueryDTO {

    @Schema(description = "Beneficiary Account ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Customer ID associated with this account", example = "550e8400-e29b-41d4-a716-446655440000")
    @JsonProperty("customer_id")
    private String customerId;
    private String firstname;
    private String lastname;
    @Schema(description = "Bank account number", example = "1234567890")
    @JsonProperty("account_number")
    private String accountNumber;
    @Schema(description = "Account holder name", example = "John Doe")
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("account_type")
    private AccountTypeDTO accountType;
    private BankDTO bank;
    @JsonProperty("customer_type")
    private CustomerTypeDTO customerType;
    @JsonProperty("customer_status")
    private CustomerStatusDTO customerStatus;
    @JsonProperty("region_code")
    private RegionCodeDTO regionCode;
    @JsonProperty("country_code")
    private CountryCodeDTO countryCode;
    private BranchDTO branch;
    @JsonProperty("qr_code")
    private QRCodeDTO qrCode;
    private CustomerDTO customer;
}
