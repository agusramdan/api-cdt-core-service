package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.base.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "cdt_vendor_crew")
@SQLDelete(sql = "UPDATE cdt_vendor_crew SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VendorCrew extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Vendor vendor;

    private String ktp;
    private String npwp;

    private UUID user_id;

    private String username;
    private String email;
    private String msisdn;

    private Boolean supplier;
    private Boolean maintenance;
    private Boolean pjpur;
    private Boolean gateway;

    @PrePersist
    protected void onCreate() {
        username = UserUtils.username(username);
    }

    @PreUpdate
    protected void onUpdate() {
        username = UserUtils.username(username);
    }
}