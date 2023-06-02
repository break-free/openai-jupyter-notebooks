
package org.apache.fineract.infrastructure.configuration.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "c_external_service", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }, name = "name_UNIQUE") })
public class ExternalService extends AbstractPersistableCustom {
    @Column(name = "name", length = 50)
    private String name;
    public static ExternalService fromJson(final JsonCommand command) {
        final String name = command.stringValueOfParameterNamed("name");
        return new ExternalService(name);
    }
    private ExternalService(final String name) {
        this.name = name;
    }
    protected ExternalService() {}
    public String name() {
        return this.name;
    }
}
