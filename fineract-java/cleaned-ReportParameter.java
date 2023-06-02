
package org.apache.fineract.infrastructure.dataqueries.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "stretchy_parameter")
public class ReportParameter extends AbstractPersistableCustom {
    protected ReportParameter() {
    }
    public boolean hasIdOf(final Long id) {
        return getId().equals(id);
    }
}
