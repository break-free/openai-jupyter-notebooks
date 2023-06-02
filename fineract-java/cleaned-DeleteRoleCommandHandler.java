
package org.apache.fineract.useradministration.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.useradministration.service.RoleWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ROLE", action = "DELETE")
public class DeleteRoleCommandHandler implements NewCommandSourceHandler {
    private final RoleWritePlatformService writePlatformService;
    @Autowired
    public DeleteRoleCommandHandler(final RoleWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.deleteRole(command.entityId());
    }
}
