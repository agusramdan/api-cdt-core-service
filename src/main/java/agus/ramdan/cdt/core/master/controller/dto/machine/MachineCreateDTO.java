package agus.ramdan.cdt.core.master.controller.dto.machine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Machine")
public class MachineCreateDTO {

    @Schema(description = "Kode Mesin")
    private String code;

    @Schema(description = "Nama Mesin")
    private String name;
    @Schema(description = "Description")
    private String description;
}
