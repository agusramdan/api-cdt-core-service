package agus.ramdan.cdt.core.master.controller.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Branch", description = "Branch")
public class BankDTO {

    @Schema(description = "System Identifier", example = "")
    private String id;

    @Schema(description = "Code", example = "Main Street")
    private String code;

    @Schema(description = "Name", example = "Kalibata")
    private String name;

}
