package agus.ramdan.cdt.core.master.controller.dto.customertype;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat Customer Type")
public class CustomerTypeCreateDTO {
    @JsonProperty(index = 1)
    private String id;

    @Schema(description = "Nama Customer Type")
    private String name;

    @Schema(description = "Deskripsi Customer Type")
    private String description;
}
