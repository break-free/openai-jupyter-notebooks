
package org.apache.fineract.portfolio.group.data;
import java.io.Serializable;
import java.util.Objects;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
public class GroupRoleData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final CodeValueData role;
    @SuppressWarnings("unused")
    private final Long clientId;
    @SuppressWarnings("unused")
    private final String clientName;
    public static final GroupRoleData template() {
        return new GroupRoleData(null, null, null, null);
    }
    public GroupRoleData(final Long id, final CodeValueData role, final Long clientId, final String clientName) {
        this.id = id;
        this.role = role;
        this.clientId = clientId;
        this.clientName = clientName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupRoleData)) {
            return false;
        }
        GroupRoleData that = (GroupRoleData) o;
        return Objects.equals(id, that.id) && Objects.equals(role, that.role) && Objects.equals(clientId, that.clientId)
                && Objects.equals(clientName, that.clientName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, role, clientId, clientName);
    }
}
