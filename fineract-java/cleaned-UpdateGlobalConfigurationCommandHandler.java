
package org.apache.fineract.infrastructure.configuration.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.configuration.service.GlobalConfigurationWritePlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CONFIGURATION", action = "UPDATE")
public class UpdateGlobalConfigurationCommandHandler implements NewCommandSourceHandler {
    private final GlobalConfigurationWritePlatformService writePlatformService;
    @Autowired
    public UpdateGlobalConfigurationCommandHandler(final GlobalConfigurationWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.update(command.entityId(), command);
    }
}
