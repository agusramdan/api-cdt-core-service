package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//indexes = {@Index(name = "idx_cdt_trx_cdm_token_signature", columnList = "token, signature")},
//uniqueConstraints = {@UniqueConstraint(columnNames = {"token", "signature"})}
@Getter
@Setter
@Entity
@Table(name = "cdt_trx_cdm")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class TrxDeposit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private String token;
    private String signature;

    @Enumerated(EnumType.STRING)
    private TrxDepositStatus status;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_crew_id")
    private CustomerCrew user;

    @ManyToOne
    @JsonProperty("service_transaction_id")
    @JsonIdentityReference(alwaysAsId = true)
    private ServiceTransaction serviceTransaction;

    @ManyToOne
    @JsonProperty("service_product_id")
    @JsonIdentityReference(alwaysAsId = true)
    private ServiceProduct serviceProduct;

    @ManyToOne
    @JsonProperty("qr_code_id")
    @JsonIdentityReference(alwaysAsId = true)
    private QRCode code;

    @ManyToOne
    @JsonProperty("beneficiary_account_id")
    @JsonIdentityReference(alwaysAsId = true)
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JsonProperty("machine_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Machine machine;
    @JsonProperty("machine_info")
    private String machineInfo;
    @Column(name = "cdm_trx_no")
    private String cdmTrxNo;
    @Column(name = "cdm_trx_datetime")
    private LocalDateTime cdmTrxDateTime;
    @Column(name = "cdm_trx_date")
    private LocalDate cdmTrxDate;
    @Column(name = "cdm_trx_time")
    private LocalTime cdmTrxTime;

    @PostLoad
    private void combineDateAndTime() {
        if (cdmTrxDate != null && cdmTrxTime != null) {
            this.cdmTrxDateTime = cdmTrxDate.atTime(cdmTrxTime);
        }
    }

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_deposit_id")
    @OrderBy("denomination")
    private List<TrxDepositDenom> denominations = new ArrayList<>();
}
