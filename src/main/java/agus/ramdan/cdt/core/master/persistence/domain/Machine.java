package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
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
@Table(name = "cdt_machine")
@SQLDelete(sql = "UPDATE cdt_machine SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Machine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;
    private String code;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("branch_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("service_location_id")
    private ServiceLocation serviceLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("supplier_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Vendor supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("maintenance_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Vendor maintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("pjpur_id")
    private Vendor pjpur;
}