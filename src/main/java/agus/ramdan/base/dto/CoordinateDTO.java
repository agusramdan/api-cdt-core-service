package agus.ramdan.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Schema(description = "Koordinat Lokasi")
public class CoordinateDTO {
    private Double latitude;
    private Double longitude;
}