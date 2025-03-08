package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cdt_trx_pickup",
        indexes = {@Index(name = "idx_cdt_trx_pickup_token_signature", columnList = "cdm_trx_no, cdm_trx_date, signature")},
        uniqueConstraints = { @UniqueConstraint(columnNames = { "cdm_trx_no", "cdm_trx_date","signature" }) }
)
@Schema
@EntityListeners(AuditingEntityListener.class)
public class TrxPickup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    private String signature;

    @Embedded
    private AuditMetadata auditMetadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @JsonProperty("machine_info")
    private String machineInfo;

    @Enumerated(EnumType.STRING)
    private TrxPickupStatus status;

    @Schema(description = "reff_id")
    private String cdm_trx_no;

    @Schema(description = "Pickup date")
    private LocalDateTime cdm_trx_date;

    private LocalDateTime trx_date;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00")
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_pickup_id")
    @OrderBy("denomination")
    private List<TrxPickupDenom> denominations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (trx_date == null) {
            trx_date = LocalDateTime.now();
        }
    }
}