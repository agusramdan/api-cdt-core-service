package agus.ramdan.cdt.core.master.controller.dto.machine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk memperbarui Machine")
public class MachineUpdateDTO {
    @Schema(description = "ID Mesin dalam format String")
    private String id;

    @Schema(description = "Kode Mesin")
    private String code;

    @Schema(description = "Nama Mesin")
    private String name;

    private String description;
}
