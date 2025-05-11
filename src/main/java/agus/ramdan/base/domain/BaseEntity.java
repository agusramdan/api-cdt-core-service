package agus.ramdan.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "deleted_at")
    @JsonProperty("deleted_at")
    private Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_on")
    @JsonProperty("created_on")
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    @JsonProperty("updated_on")
    private Timestamp updatedOn;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonProperty("created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonProperty("updated_by")
    private String updatedBy;

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
