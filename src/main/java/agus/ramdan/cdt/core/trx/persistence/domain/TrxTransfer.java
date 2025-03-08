package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TrxTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;
    @Column(name = "trx_date")
    private LocalDateTime trxDate;
    @Enumerated(EnumType.STRING)
    private TrxTransferStatus status;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private ServiceTransaction transaction;
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "beneficiary_account_id")
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
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
