package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
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
@Table(name = "cdt_trx_cdm")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class TrxDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

    @Column(unique = true)
    private String token;
    private String signature;

    @Enumerated(EnumType.STRING)
    private TrxDepositStatus status;

    @ManyToOne
    private CustomerCrew user;

    @ManyToOne
    @JsonProperty("service_transaction")
    private ServiceTransaction serviceTransaction;

    @ManyToOne
    @JsonProperty("service_product")
    private ServiceProduct serviceProduct;

    @ManyToOne
    private QRCode code;

    @ManyToOne
    private Machine machine;

    private String cdm_trx_no;

    private LocalDateTime cdm_trx_date;

    private LocalDateTime trx_date;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_cdm_id")
    @OrderBy("denomination")
    private List<TrxDepositDenom> denominations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (trx_date == null) {
            trx_date = LocalDateTime.now();
        }
    }
}