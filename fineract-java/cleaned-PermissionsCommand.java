
package org.apache.fineract.useradministration.command;
import java.util.Map;
public class PermissionsCommand {
    private final Map<String, Boolean> permissions;
    public PermissionsCommand(final Map<String, Boolean> permissionsMap) {
        this.permissions = permissionsMap;
    }
    public Map<String, Boolean> getPermissions() {
        return this.permissions;
    }
}
