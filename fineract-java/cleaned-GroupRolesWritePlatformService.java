
package org.apache.fineract.portfolio.group.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GroupRolesWritePlatformService {
    CommandProcessingResult createRole(JsonCommand command);
    CommandProcessingResult updateRole(JsonCommand command);
    CommandProcessingResult deleteRole(Long ruleId);
}
