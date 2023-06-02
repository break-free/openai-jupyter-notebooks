
package org.apache.fineract.useradministration.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.useradministration.service.RoleWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "ROLE", action = "DISABLE")
public class DisableRoleCommandHandler implements NewCommandSourceHandler {
    private final RoleWritePlatformService writePlatformService;
    @Autowired
    public DisableRoleCommandHandler(final RoleWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.disableRole(command.entityId());
    }
}
