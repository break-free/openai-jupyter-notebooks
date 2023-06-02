
package org.apache.fineract.portfolio.group.domain;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.group.api.GroupingTypesApiConstants;
@Entity
@Table(name = "m_group_roles")
public class GroupRole extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_cv_id")
    private CodeValue role;
    public GroupRole() {
    }
    public static final GroupRole createGroupRole(final Group group, final Client client, final CodeValue role) {
        return new GroupRole(group, client, role);
    }
    public GroupRole(final Group group, final Client client, final CodeValue role) {
        this.group = group;
        this.client = client;
        this.role = role;
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(2);
        if (command.isChangeInLongParameterNamed(GroupingTypesApiConstants.clientIdParamName, this.client.getId())) {
            final Long newValue = command.longValueOfParameterNamed(GroupingTypesApiConstants.clientIdParamName);
            actualChanges.put(GroupingTypesApiConstants.clientIdParamName, newValue);
        }
        if (command.isChangeInLongParameterNamed(GroupingTypesApiConstants.roleParamName, this.role.getId())) {
            final Long newValue = command.longValueOfParameterNamed(GroupingTypesApiConstants.roleParamName);
            actualChanges.put(GroupingTypesApiConstants.roleParamName, newValue);
        }
        return actualChanges;
    }
    public void updateRole(final CodeValue role) {
        this.role = role;
    }
    public void updateClient(final Client client) {
        this.client = client;
    }
}
