package agus.ramdan.cdt.core.master.controller.dto.gateway;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Gateway")
public class GatewayQueryDTO {
    @Schema(description = "ID Gateway dalam format String")
    private String id;

    @Schema(description = "Kode Gateway")
    private String code;

    @Schema(description = "Nama Gateway")
    private String name;

    @Schema(description = "Deskripsi Gateway")
    private String description;

    @Schema(description = "ID Vendor sebagai Partner")
    private String partnerId;
}
