package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
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
public class ServiceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(length = 20, updatable = false, unique = true)
    private String no;

    @Embedded
    private AuditMetadata auditMetadata;

    private TrxStatus status;
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrxDeposit deposit;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrxTransfer transfer;
}