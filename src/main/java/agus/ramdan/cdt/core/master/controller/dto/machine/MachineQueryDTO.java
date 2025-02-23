package agus.ramdan.cdt.core.master.controller.dto.machine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Machine")
public class MachineQueryDTO {
    private String id;
    private String code;
    private String name;
    private String description;
}
