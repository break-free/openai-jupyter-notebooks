
package org.apache.fineract.infrastructure.entityaccess.domain;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_entity_relation")
public class FineractEntityRelation extends AbstractPersistableCustom {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relationId", orphanRemoval = true)
    private Set<FineractEntityToEntityMapping> fineractEntityToEntityMapping = new HashSet<>();
    @Column(name = "from_entity_type", nullable = false, length = 10)
    private String fromEntityType;
    @Column(name = "to_entity_type", nullable = false, length = 10)
    private String toEntityType;
    @Column(name = "code_name", nullable = false, length = 50)
    private String codeName;
    public FineractEntityRelation() {
    }
    public Set<FineractEntityToEntityMapping> getFineractEntityToEntityMapping() {
        return this.fineractEntityToEntityMapping;
    }
    public void setFineractEntityToEntityMapping(Set<FineractEntityToEntityMapping> fineractEntityToEntityMapping) {
        this.fineractEntityToEntityMapping = fineractEntityToEntityMapping;
    }
    public String getFromEntityType() {
        return this.fromEntityType;
    }
    public void setFromEntityType(String fromEntityType) {
        this.fromEntityType = fromEntityType;
    }
    public String getToEntityType() {
        return this.toEntityType;
    }
    public void setToEntityType(String toEntityType) {
        this.toEntityType = toEntityType;
    }
    public String getCodeName() {
        return this.codeName;
    }
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
