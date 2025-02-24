package agus.ramdan.cdt.core.master.controller.dto.bank;

import agus.ramdan.base.dto.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Bank")
public class BankQueryDTO {
    @Schema(description = "ID Bank dalam format String")
    private String id;

    @Schema(description = "Kode Bank")
    private String code;

    @Schema(description = "Nama Bank")
    private String name;

    @Schema(description = "Alamat Bank")
    private AddressDTO address;
}
