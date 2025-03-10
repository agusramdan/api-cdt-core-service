package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.CodeOrID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Service Location")
public class ServiceLocationDTO implements CodeOrID<String> {
    @Schema(description = "ID Lokasi dalam format String")
    private String id;

    @Schema(description = "Kode Lokasi")
    private String code;

    @Schema(description = "Nama Lokasi")
    private String name;

}
