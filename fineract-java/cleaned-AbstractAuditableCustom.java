
package org.apache.fineract.infrastructure.core.domain;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractAuditable;
@MappedSuperclass
public abstract class AbstractAuditableCustom extends AbstractPersistableCustom implements Auditable<Long, Long, LocalDateTime> {
    private static final long serialVersionUID = 141481953116476081L;
    @Column(name = "createdby_id")
    private Long createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "lastmodifiedby_id")
    private Long lastModifiedBy;
    @Column(name = "lastmodified_date")
    private LocalDateTime lastModifiedDate;
    @Override
    public Optional<Long> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }
    @Override
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }
    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return null == this.createdDate ? Optional.empty() : Optional.of(this.createdDate);
    }
    @Override
    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    @Override
    public Optional<Long> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }
    @Override
    public void setLastModifiedBy(final Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return null == this.lastModifiedDate ? Optional.empty() : Optional.of(this.lastModifiedDate);
    }
    @Override
    public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
