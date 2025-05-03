package agus.ramdan.cdt.core.master.controller.dto.product;

import agus.ramdan.cdt.core.master.controller.dto.PjpurRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.QRRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.TransferRuleConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Service Product")
public class ServiceProductQueryDTO {
    @Schema(description = "ID Produk dalam format String")
    private String id;

    @Schema(description = "Kode Produk")
    private String code;

    @Schema(description = "Nama Produk")
    private String name;

    @Schema(description = "Description Produk")
    private String description;
    @JsonProperty("service_rule_config")
    private ServiceRuleConfig serviceRuleConfig;
    @JsonProperty("qr_rule_config")
    private QRRuleConfig qrRuleConfig;

    @JsonProperty("pjpur_rule_config")
    private PjpurRuleConfig pjpurRuleConfig;

    @JsonProperty("transfer_rule_config")
    private TransferRuleConfig transferRuleConfig;
}
