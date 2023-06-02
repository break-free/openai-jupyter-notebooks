
package org.apache.fineract.infrastructure.hooks.domain;
import static org.apache.fineract.infrastructure.hooks.api.HookApiConstants.nameParamName;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_hook_templates")
public class HookTemplate extends AbstractPersistableCustom {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "template", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Schema> fields = new HashSet<>();
    private HookTemplate(final String name) {
        if (StringUtils.isNotBlank(name)) {
            this.name = name.trim();
        } else {
            this.name = null;
        }
    }
    protected HookTemplate() {
    }
    public static HookTemplate fromJson(final JsonCommand command) {
        final String name = command.stringValueOfParameterNamed(nameParamName);
        return new HookTemplate(name);
    }
    public String getName() {
        return this.name;
    }
    public Set<Schema> getSchema() {
        return this.fields;
    }
}
