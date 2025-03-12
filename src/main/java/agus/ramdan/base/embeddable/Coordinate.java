package agus.ramdan.base.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Coordinate {
    private Double longitude;
    private Double latitude;
}
