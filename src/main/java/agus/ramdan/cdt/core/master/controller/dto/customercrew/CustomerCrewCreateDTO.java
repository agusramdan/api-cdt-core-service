package agus.ramdan.cdt.core.master.controller.dto.customercrew;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Customer Crew")
public class CustomerCrewCreateDTO {
    @Schema(description = "Nama Crew")
    private String name;

    @Schema(description = "KTP Crew")
    private String ktp;

    @Schema(description = "NPWP Crew")
    private String npwp;

    @Schema(description = "ID Customer")
    @JsonProperty("customer_id")
    private String customerId;

    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    @Email
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msidn;
}
