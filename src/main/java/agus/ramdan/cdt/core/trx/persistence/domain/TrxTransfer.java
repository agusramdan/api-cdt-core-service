package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cdt_transfer")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrxTransfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "trx_date")
    private LocalDateTime trxDate;

    @Column(name = "trx_no")
    private String trxNo;


    @Enumerated(EnumType.STRING)
    private TrxTransferStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("transaction_id")
    private ServiceTransaction transaction;

    //@Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_account_id")
    @JsonProperty("beneficiary_account_id")
    @JsonIdentityReference(alwaysAsId = true)
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id")
    @JsonProperty("gateway_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Gateway gateway;

    private String description;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = TrxTransferStatus.PREPARE;
        }
        if (trxDate == null) {
            trxDate = LocalDateTime.now();
        }
    }
}
