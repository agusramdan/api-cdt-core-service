package agus.ramdan.base.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Koordinat Lokasi")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoordinateDTO {
    private Double latitude;
    private Double longitude;
}