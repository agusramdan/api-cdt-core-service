package agus.ramdan.cdt.core.master.controller.dto.bank;

import agus.ramdan.base.dto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Bank")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BankQueryDTO {
    @Schema(description = "ID Bank dalam format String")
    private String id;

    @Schema(description = "Kode Bank")
    private String code;
    @JsonProperty("bank_payer_id")
    @Schema(description = "itsjeck Bank Payer ID")
    private Integer bankPayerId;
    @Schema(description = "Nama Bank")
    private String name;
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
