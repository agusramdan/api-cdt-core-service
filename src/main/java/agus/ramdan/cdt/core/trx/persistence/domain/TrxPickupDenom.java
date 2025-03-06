package agus.ramdan.cdt.core.trx.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cdt_trx_cdm_de")
@Schema
public class TrxPickupDenom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trx_pickup_id")
    @JsonIgnore
    private TrxPickup trxPickup;

    @Column(name = "denomination", precision = 12, scale = 2, nullable = false)
    private BigDecimal denomination;

    private Integer quantity;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    @JsonProperty(index = 5)
    protected BigDecimal amount;

    @Pattern(regexp = "coin|note", message = "Invalid type. Only 'coin' or 'note' are allowed.")
    private String type;

    @PrePersist
    protected void onCreate() {
        if (type == null) {
            type = "note";
        }
    }
}