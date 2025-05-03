package agus.ramdan.cdt.core.master.controller.dto.product;

import agus.ramdan.cdt.core.master.controller.dto.PjpurRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.QRRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.TransferRuleConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Service Product")
public class ServiceProductCreateDTO {
    @Schema(description = "Kode Produk")
    private String code;

    @Schema(description = "Nama Produk")
    private String name;

    @Schema(description = "Description Produk")
    private String description;

    @JsonProperty("qr_rule_config")
    private QRRuleConfig qrRuleConfig;

    @JsonProperty("pjpur_rule_config")
    private PjpurRuleConfig pjpurRuleConfig;

    @JsonProperty("transfer_rule_config")
    private TransferRuleConfig transferRuleConfig;

}
