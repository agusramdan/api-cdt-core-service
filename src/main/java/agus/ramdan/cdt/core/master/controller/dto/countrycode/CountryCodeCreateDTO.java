package agus.ramdan.cdt.core.master.controller.dto.countrycode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat Country Code")
public class CountryCodeCreateDTO {
    @JsonProperty(index = 1)
    private String id;

    @Schema(description = "Nama Country Code")
    private String name;

    @Schema(description = "Deskripsi Country Code")
    private String description;
}
