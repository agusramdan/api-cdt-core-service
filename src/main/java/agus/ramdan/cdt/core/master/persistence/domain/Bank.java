package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.Address;
import agus.ramdan.base.embeddable.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
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
@FilterDef(name = "deletedFilter_cdt_bank", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter_cdt_bank", condition = "deleted_at IS NULL") // Pengganti
@Where(clause = "deleted_at IS NULL")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private String name;
    private String code;
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