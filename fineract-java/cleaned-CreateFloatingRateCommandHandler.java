
package org.apache.fineract.portfolio.floatingrates.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.floatingrates.service.FloatingRateWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "FLOATINGRATE", action = "CREATE")
public class CreateFloatingRateCommandHandler implements NewCommandSourceHandler {
    private final FloatingRateWritePlatformService writePlatformService;
    @Autowired
    public CreateFloatingRateCommandHandler(final FloatingRateWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createFloatingRate(command);
    }
}
