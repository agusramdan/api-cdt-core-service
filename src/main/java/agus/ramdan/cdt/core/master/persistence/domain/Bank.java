package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.Address;
import agus.ramdan.base.embeddable.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
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
@Filter(name = "deletedFilter_cdt_bank", condition = "deleted_at IS NULL") // Pengganti @Where
@Schema
@EntityListeners(AuditingEntityListener.class)
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

    private String name;
    private String code;
    private Boolean onlineTransfer;
    private Boolean biFastTransfer;
    private Boolean wallet;
    private Boolean virtualAccount;

    // Address
    @Embedded
    private Address address;  // Embedded Address

}