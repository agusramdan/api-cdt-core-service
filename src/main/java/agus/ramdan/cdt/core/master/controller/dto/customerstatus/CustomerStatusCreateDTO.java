package agus.ramdan.cdt.core.master.controller.dto.customerstatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat Customer Status")
public class CustomerStatusCreateDTO {
    @JsonProperty(index = 1)
    private String id;

    @Schema(description = "Nama Customer Status")
    private String name;

    @Schema(description = "Deskripsi Customer Status")
    private String description;
}

