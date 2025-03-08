package agus.ramdan.cdt.core.master.controller.dto.beneficiary;

import agus.ramdan.cdt.core.master.controller.dto.*;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BeneficiaryAccountUpdateDTO", description = "DTO for updating an existing beneficiary account")
public class BeneficiaryAccountUpdateDTO {

    @Schema(description = "Bank account number (optional)", example = "1234567890")
    private String account_number;

    @Schema(description = "Account holder name (optional)", example = "John Doe")
    private String account_name;

    private AccountTypeDTO accountType;

    private BankDTO bank;

    private CustomerTypeDTO customerType;

    private CustomerStatusDTO customerStatus;

    private RegionCodeDTO regionCode;

    private CountryCodeDTO countryCode;

    private BranchDTO branch;

    private QRCodeDTO qrCode;
}
