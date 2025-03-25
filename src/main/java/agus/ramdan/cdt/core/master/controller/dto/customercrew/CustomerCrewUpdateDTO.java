package agus.ramdan.cdt.core.master.controller.dto.customercrew;

import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk memperbarui Customer Crew")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerCrewUpdateDTO {
    @Schema(description = "ID Crew dalam format String")
    private String id;

    @Schema(description = "Nama Crew")
    private String name;

    @Schema(description = "KTP Crew")
    private String ktp;

    @Schema(description = "NPWP Crew")
    private String npwp;

    @Schema(description = "ID Customer / deprecated")
    @JsonProperty("customer_id")
    private String customerId;

    private CustomerDTO customer;

    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msisdn;
}
