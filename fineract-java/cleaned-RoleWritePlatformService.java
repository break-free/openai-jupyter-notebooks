
package org.apache.fineract.useradministration.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface RoleWritePlatformService {
    CommandProcessingResult createRole(JsonCommand command);
    CommandProcessingResult updateRole(Long roleId, JsonCommand command);
    CommandProcessingResult updateRolePermissions(Long roleId, JsonCommand command);
    CommandProcessingResult deleteRole(Long roleId);
    CommandProcessingResult disableRole(Long roleId);
    CommandProcessingResult enableRole(Long roleId);
}
