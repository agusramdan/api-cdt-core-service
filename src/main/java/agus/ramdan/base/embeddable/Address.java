package agus.ramdan.base.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

    private String building;
    private String street;
    private String region;
    private String city;
    private String zipCode;
    private String state;
    private String country;
}
