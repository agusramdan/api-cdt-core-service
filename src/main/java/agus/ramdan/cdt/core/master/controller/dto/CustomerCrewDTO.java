package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.TID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Customer Crew")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerCrewDTO implements TID<String> {
    @Schema(description = "ID Crew dalam format String")
    private String id;

    @Schema(description = "Nama")
    private String name;

//    @JsonProperty("customer_id")
//    private String customerId;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "MSIDN")
    private String msisdn;
}
