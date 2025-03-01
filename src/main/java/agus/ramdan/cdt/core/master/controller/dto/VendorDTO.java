package agus.ramdan.cdt.core.master.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Vendor")
public class VendorDTO {
    @Schema(description = "ID Vendor dalam format String")
    private String id;

    @Schema(description = "Kode Vendor")
    private String code;

    @Schema(description = "Nama Vendor")
    private String name;

}
