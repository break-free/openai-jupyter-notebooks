
package org.apache.fineract.infrastructure.hooks.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_hook_schema")
public class Schema extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "hook_template_id", referencedColumnName = "id", nullable = false)
    private HookTemplate template;
    @Column(name = "field_type", nullable = false, length = 20)
    private String fieldType;
    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;
    @Column(name = "placeholder", length = 100)
    private String placeholder;
    @Column(name = "optional", nullable = false)
    private boolean optional = false;
    public Schema() {
    }
    public String getFieldName() {
        return this.fieldName;
    }
    public String getFieldType() {
        return this.fieldType;
    }
    public boolean isOptional() {
        return this.optional;
    }
}
