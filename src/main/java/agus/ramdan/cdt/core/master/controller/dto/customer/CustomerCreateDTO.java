package agus.ramdan.cdt.core.master.controller.dto.customer;

import agus.ramdan.base.dto.AddressDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "CustomerCreateDTO", description = "DTO for creating a customer")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerCreateDTO {

    @NotBlank
    @Schema(description = "Customer Full Name", example = "John Doe")
    private String name;

    @JsonProperty("customer_type")
    @NotNull
    @Schema(description = "Customer Type (INDIVIDUAL or BUSINESS)", example = "INDIVIDUAL")
    private String customerType;

    @JsonProperty("ktp")
    private String ktp;

    @JsonProperty("npwp")
    private String npwp;

    @Email
    @Schema(description = "Customer Email Address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format")
    @Schema(description = "Customer Mobile Number", example = "+6281234567890")
    private String msisdn;

    @Schema(description = "Customer Address Details")
    private AddressDTO address;

    @Schema(description = "Customer managed By branch")
    private BranchDTO branch;
}
