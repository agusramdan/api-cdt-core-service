package agus.ramdan.cdt.core.master.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_vendor_crew")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class VendorCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_on;
    @UpdateTimestamp
    private LocalDateTime updated_on;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updated_by;

    @ManyToOne
    @JoinColumn(name= "vendor_id")
    private Vendor vendor;

    private String ktp;
    private String npwp;

    private UUID user_id;

    private String username;
    private String email;
    private String msidn;
}