package agus.ramdan.cdt.core.master.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Gateway")
public class GatewayDTO {
    @Schema(description = "ID Gateway dalam format String")
    private String id;

    @Schema(description = "Kode Gateway")
    private String code;

    @Schema(description = "Nama Gateway")
    private String name;

}
