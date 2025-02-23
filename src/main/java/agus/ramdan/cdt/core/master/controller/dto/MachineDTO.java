package agus.ramdan.cdt.core.master.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MachineDTO", description = "DTO untuk mesin")
public class MachineDTO {
    private String id;
    private String code;
    private String name;
    private String description;
}
