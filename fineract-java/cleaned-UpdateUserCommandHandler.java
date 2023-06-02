
package org.apache.fineract.useradministration.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.useradministration.service.AppUserWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "USER", action = "UPDATE")
public class UpdateUserCommandHandler implements NewCommandSourceHandler {
    private final AppUserWritePlatformService writePlatformService;
    @Autowired
    public UpdateUserCommandHandler(final AppUserWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        final Long userId = command.entityId();
        return this.writePlatformService.updateUser(userId, command);
    }
}
