package agus.ramdan.cdt.core.master.controller.dto.bank;

import agus.ramdan.base.dto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk memperbarui Bank")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BankUpdateDTO {

    @Schema(description = "Kode Bank")
    private String code;

    @Schema(description = "Nama Bank")
    private String name;
    @JsonProperty("bank_payer_id")
    @Schema(description = "itsjeck Bank Payer ID")
    private Integer bankPayerId;
    @JsonProperty("online_transfer")
    private Boolean onlineTransfer;
    @JsonProperty("bi_fast_transfer")
    private Boolean biFastTransfer;
    private Boolean wallet;
    @JsonProperty("virtual_account")
    private Boolean virtualAccount;
    @Schema(description = "Alamat Bank")
    private AddressDTO address;
}
