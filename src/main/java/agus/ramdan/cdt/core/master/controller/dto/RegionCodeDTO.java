package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RegionCode", description = "Region Code")
//@JsonDeserialize(using = GenericIdDeserializer.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RegionCodeDTO implements HasId {

    @Schema(description = "System", example = "")
    private String id;

    @Schema(description = "Name", example = "Saving")
    private String name;

}
