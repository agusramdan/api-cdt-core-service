package agus.ramdan.cdt.core.trx.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cdt_trx_pjpur_de")
@Schema
public class TrxDepositPjpurDenom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trx_pjpur_id")
    @JsonIgnore
    private TrxDepositPjpur trxDepositPjpur;

    //@Column(name = "denomination", precision = 12, scale = 2, nullable = false)
    private BigDecimal denomination;

    private Integer quantity;

}