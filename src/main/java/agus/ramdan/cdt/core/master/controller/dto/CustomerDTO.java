package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.TID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "CustomerDTO", description = "DTO for querying customer details")
public class CustomerDTO implements TID<String> {
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format for customer_id")
    @Schema(description = "Unique Customer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Customer Full Name", example = "John Doe")
    private String name;

    @Schema(description = "Relation lookup KTP")
    private String ref;
}

