package agus.ramdan.base.embeddable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Address {
    private String building;
    private String street;
    private String region;
    private String city;
    private String zipCode;
    private String state;
    private String country;
}
