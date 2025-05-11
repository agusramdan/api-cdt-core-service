package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.domain.VendorCrew;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
//indexes = {@Index(name = "idx_cdt_trx_pickup_token_signature", columnList = "cdm_trx_no, cdm_trx_date, signature")}
@Getter
@Setter
@Entity
@Table(name = "cdt_trx_pickup")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrxPickup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private String username;

    @Enumerated(EnumType.STRING)
    @JsonProperty("pjpur_status")
    private TrxDepositPjpurStatus pjpurStatus;

    @ManyToOne
    @JsonProperty("vendor_crew_id")
    @JsonIdentityReference(alwaysAsId = true)
    private VendorCrew user;
//    @ManyToOne
//    private QRCode qrCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @JsonProperty("machine_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Machine machine;

    @JsonProperty("machine_info")
    private String machineInfo;

    @Enumerated(EnumType.STRING)
    private TrxPickupStatus status;

    @Schema(description = "reff_id")
    @Column(name="cdm_trx_no")
    private String cdmTrxNo;

    @Schema(description = "Pickup date")
    @Column(name="cdm_trx_date")
    private LocalDate cdmTrxDate;

    @Column(name="cdm_trx_time")
    @JsonProperty("cdm_trx_time")
    private LocalTime cdmTrxTime;

    @JsonIgnore
    public LocalDateTime getCdmTrxDateTime() {
        return LocalDateTime.of(cdmTrxDate, cdmTrxTime);
    }

    @JsonProperty("cdm_trx_type")
    private String cdmTrxType;
    private Integer totalPieces;
    private String newBanknoteBagNo;
    private String oldBanknoteBagNo;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00")
    private BigDecimal amount;
    private String otpUsed;
    private LocalDateTime resetBagTime;
    private String lastAction;

    private LocalDate actionDate;

    private LocalTime actionStartTime;

    private LocalTime actionEndTime;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_pickup_id")
    @OrderBy("denomination")
    private List<TrxPickupDenom> denominations = new ArrayList<>();

}