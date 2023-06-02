
package org.apache.fineract.portfolio.rate.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.rate.service.RateWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "RATE", action = "CREATE")
public class CreateRateCommandHandler implements NewCommandSourceHandler {
    private final RateWriteService writePlatformService;
    @Autowired
    public CreateRateCommandHandler(final RateWriteService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createRate(command);
    }
}
