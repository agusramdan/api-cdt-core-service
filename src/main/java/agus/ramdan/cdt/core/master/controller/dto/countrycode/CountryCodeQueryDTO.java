package agus.ramdan.cdt.core.master.controller.dto.countrycode;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca Country Code")
public class CountryCodeQueryDTO {
    @Schema(description = "ID Country Code dalam format String")
    private String id;

    @Schema(description = "Nama Country Code")
    private String name;

    @Schema(description = "Deskripsi Country Code")
    private String description;
}
