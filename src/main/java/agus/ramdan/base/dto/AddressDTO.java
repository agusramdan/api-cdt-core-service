package agus.ramdan.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AddressDTO", description = "Address Information")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressDTO {

    @Schema(description = "Building or Apartment Name", example = "Tower A")
    private String building;

    @Schema(description = "Street Name", example = "Main Street")
    private String street;

    @Schema(description = "Region or State", example = "Kalibata")
    private String region;

    @Schema(description = "City", example = "Jakarta Selatan")
    private String city;

    @Schema(description = "State", example = "DK Jakarta")
    private String state;

    @Schema(description = "ZIP Code", example = "10110")
    @JsonProperty("zip_code")
    private String zipCode;

    @Schema(description = "Country", example = "Indonesia")
    private String country;
}
