package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.*;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountUpdateDTO", description = "DTO for updating an existing beneficiary account")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BeneficiaryAccountUpdateDTO {
    private String firstname;
    private String lastname;
    @Schema(description = "Bank account number (optional)", example = "1234567890")
    private String accountNumber;
    @Schema(description = "Account holder name (optional)", example = "John Doe")
    private String accountName;
    private AccountTypeDTO accountType;
    private BankDTO bank;
    private CustomerTypeDTO customerType;
    private CustomerStatusDTO customerStatus;
    private RegionCodeDTO regionCode;
    private CountryCodeDTO countryCode;
    private BranchDTO branch;
    private QRCodeDTO qrCode;
}
