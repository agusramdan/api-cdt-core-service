package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.TID;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Customer Crew")
public class CustomerCrewDTO implements TID<String> {
    @Schema(description = "ID Crew dalam format String")
    private String id;

    @Schema(description = "Nama Crew")
    private String name;

    @JsonProperty("customer_id")
    private String customerId;

    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msisdn;
}
