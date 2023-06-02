
package org.apache.fineract.infrastructure.campaigns.email.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "scheduled_email_configuration")
public class EmailConfiguration extends AbstractPersistableCustom {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value", nullable = false)
    private String value;
    protected EmailConfiguration() {}
    public EmailConfiguration(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return this.name;
    }
    public String getValue() {
        return this.value;
    }
}
