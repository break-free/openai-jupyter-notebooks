
package org.apache.fineract.portfolio.address.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_field_configuration")
public class FieldConfiguration extends AbstractPersistableCustom {
    private String entity;
    private String table;
    private String field;
    private boolean isEnabled;
    public FieldConfiguration() {
    }
    public FieldConfiguration(final String entity, final String table, final String field, final boolean is_enabled) {
        this.entity = entity;
        this.table = table;
        this.field = field;
        this.isEnabled = is_enabled;
    }
}
