package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.base.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "cdt_customer_crew")
@SQLDelete(sql = "UPDATE cdt_customer_crew SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedFilter_cdt_customer_crew", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter_cdt_customer_crew", condition = "deleted_at IS NULL") // Pengganti @Where
@Where(clause = "deleted_at IS NULL")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class CustomerCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @Embedded
    private AuditMetadata auditMetadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String ktp;
    private String npwp;
    private UUID user_id;
    private String username;
    private String email;
    private String msisdn;

    @PrePersist
    protected void onCreate() {
        username = UserUtils.username(username);
    }

    @PreUpdate
    protected void onUpdate() {
        username = UserUtils.username(username);
    }
}