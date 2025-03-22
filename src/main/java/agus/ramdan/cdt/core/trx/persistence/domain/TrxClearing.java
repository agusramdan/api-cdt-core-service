package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
@Table(name = "cdt_clearing")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class TrxClearing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private LocalDateTime trx_date;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    protected BigDecimal amount;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "beneficiary_account_id")
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "gateway_id")
    private Gateway gateway;

    @PrePersist
    protected void onCreate() {
        if (trx_date == null) {
            trx_date = LocalDateTime.now();
        }
    }
}