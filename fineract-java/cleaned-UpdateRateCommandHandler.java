
package org.apache.fineract.portfolio.rate.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.rate.service.RateWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "RATE", action = "UPDATE")
public class UpdateRateCommandHandler implements NewCommandSourceHandler {
    private final RateWriteService writePlatformService;
    @Autowired
    public UpdateRateCommandHandler(final RateWriteService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateRate(command.entityId(), command);
    }
}
