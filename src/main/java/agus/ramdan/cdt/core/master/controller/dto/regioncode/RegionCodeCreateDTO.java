package agus.ramdan.cdt.core.master.controller.dto.regioncode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat Region Code")
public class RegionCodeCreateDTO {
    @JsonProperty(index = 1)
    private String id;

    @Schema(description = "Nama Region Code")
    private String name;

    @Schema(description = "Deskripsi Region Code")
    private String description;
}
