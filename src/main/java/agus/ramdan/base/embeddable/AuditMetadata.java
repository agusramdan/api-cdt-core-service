package agus.ramdan.base.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
public class AuditMetadata {
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created_on;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updated_on;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updated_by;

    // Getters and Setters


}
