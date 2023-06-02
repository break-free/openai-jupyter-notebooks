
package org.apache.fineract.infrastructure.core.domain;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;
@MappedSuperclass
public abstract class AbstractPersistableCustom implements Persistable<Long>, Serializable {
    private static final long serialVersionUID = 9181640245194392646L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private boolean isNew = true;
    @Override
    public Long getId() {
        return id;
    }
    protected void setId(final Long id) {
        this.id = id;
    }
    @Override
    public boolean isNew() {
        return isNew;
    }
    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
