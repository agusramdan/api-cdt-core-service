package agus.ramdan.cdt.core.master.controller.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RegionCode", description = "Region Code")
public class RegionCodeDTO {

    @Schema(description = "System", example = "")
    private String id;

    @Schema(description = "Name", example = "Saving")
    private String name;

}
