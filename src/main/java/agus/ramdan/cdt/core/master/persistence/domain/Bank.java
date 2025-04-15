package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.Address;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_bank")
@SQLDelete(sql = "UPDATE cdt_bank SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private String name;
    private String code;
    @JsonProperty("bank_payer_id")
    @Schema(description = "itsjeck Bank Payer ID")
    private Integer bankPayerId;
    @JsonProperty("online_transfer")
    private Boolean onlineTransfer;
    @JsonProperty("bi_fast_transfer")
    private Boolean biFastTransfer;
    private Boolean wallet;
    @JsonProperty("virtual_account")
    private Boolean virtualAccount;
    // Address
    @Embedded
    private Address address;  // Embedded Address

}