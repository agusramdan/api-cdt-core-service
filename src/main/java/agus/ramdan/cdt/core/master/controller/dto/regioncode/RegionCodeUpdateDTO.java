package agus.ramdan.cdt.core.master.controller.dto.regioncode;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk memperbarui Region Code")
public class RegionCodeUpdateDTO {
    @Schema(description = "ID Region Code dalam format String")
    private String id;

    @Schema(description = "Nama Region Code")
    private String name;

    @Schema(description = "Deskripsi Region Code")
    private String description;
}
