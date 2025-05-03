package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service_trx")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(length = 20, updatable = false, unique = true)
    private String no;
    private LocalDateTime trxDate;
    private TrxStatus status;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @ManyToOne
    @JsonProperty("service_product_id")
    @JsonIdentityReference(alwaysAsId = true)
    private ServiceProduct serviceProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private TrxDeposit deposit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private TrxDepositPjpur depositPjpur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private TrxTransfer transfer;

    @PrePersist
    protected void onCreate() {
        if (trxDate == null) {
            trxDate = LocalDateTime.now();
        }
    }
}