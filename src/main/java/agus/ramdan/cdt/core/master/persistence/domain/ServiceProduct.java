package agus.ramdan.cdt.core.master.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
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
@Table(name = "cdt_product")
@SQLDelete(sql = "UPDATE cdt_product SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class ServiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    @JsonProperty("created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    @JsonProperty("updated_on")
    private LocalDateTime updatedOn;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonProperty("created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonProperty("updated_by")
    private String updatedBy;

    private String code;
    private String name;

    private boolean deleted;
}