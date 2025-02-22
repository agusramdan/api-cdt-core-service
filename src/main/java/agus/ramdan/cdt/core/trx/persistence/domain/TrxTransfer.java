package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_on;
    @UpdateTimestamp
    private LocalDateTime updated_on;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    public String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    public String updated_by;

    private LocalDateTime trx_date;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    protected BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "beneficiary_account_id")
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Gateway gateway;

    @PrePersist
    protected void onCreate() {
        if (trx_date == null) {
            trx_date = LocalDateTime.now();
        }
    }
}