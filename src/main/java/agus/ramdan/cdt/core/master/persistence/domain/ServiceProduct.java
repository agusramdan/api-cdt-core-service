package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.controller.dto.PjpurRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.QRRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.controller.dto.TransferRuleConfig;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_product")
@SQLDelete(sql = "UPDATE cdt_product SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceProduct extends BaseEntity {
    /**
     * Multiple use QR
     */
    public static final String MUL_ST_TR = "MUL-ST-TR";
    /**
     * Single use QR
     */
    public static final String SUS_ST_TR = "SQR-ST-TR";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;
    private String code;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    @JsonProperty("service_rule_config")
    private ServiceRuleConfig serviceRuleConfig;
    @Enumerated(EnumType.STRING)
    @JsonProperty("qr_rule_config")
    private QRRuleConfig qrRuleConfig;
    @Enumerated(EnumType.STRING)
    @JsonProperty("pjpur_rule_config")
    private PjpurRuleConfig pjpurRuleConfig;
    @Enumerated(EnumType.STRING)
    @JsonProperty("transfer_rule_config")
    private TransferRuleConfig transferRuleConfig;

}