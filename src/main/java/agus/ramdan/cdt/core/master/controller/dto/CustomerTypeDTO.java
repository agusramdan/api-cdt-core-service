package agus.ramdan.cdt.core.master.controller.dto;
import agus.ramdan.base.dto.HasId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CustomerType", description = "Customer Type")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CustomerTypeDTO implements HasId {

    @Schema(description = "System", example = "SVN")
    private String id;

    @Schema(description = "Name", example = "Saving")
    private String name;

}
