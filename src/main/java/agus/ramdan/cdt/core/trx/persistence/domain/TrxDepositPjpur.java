package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//indexes = {@Index(name = "idx_cdt_trx_cdm_token_signature", columnList = "token, signature")},
//uniqueConstraints = {@UniqueConstraint(columnNames = {"token", "signature"})}
@Getter
@Setter
@Entity
@Table(name = "cdt_trx_pjpur")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrxDepositPjpur extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    @Version
    private int version;

    @Enumerated(EnumType.STRING)
    private TrxDepositPjpurStatus status;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_crew_id")
    private CustomerCrew user;

    @ManyToOne
    @JsonProperty("service_transaction_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private ServiceTransaction serviceTransaction;

    @ManyToOne
    @JsonProperty("beneficiary_account_id")
    @JsonIdentityReference(alwaysAsId = true)
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JsonProperty("machine_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Machine machine;

    @Column(name = "trx_no")
    private String trxNo;

    @Column(name = "trx_date_time")
    private LocalDateTime trxDateTime;

    //@Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_deposit_id")
    @OrderBy("denomination")
    private List<TrxDepositPjpurDenom> denominations = new ArrayList<>();
}
