
package org.apache.fineract.portfolio.interestratechart.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.interestratechart.service.InterestRateChartWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "INTERESTRATECHART", action = "UPDATE")
public class UpdateInterestRateChartCommandHandler implements NewCommandSourceHandler {
    private final InterestRateChartWritePlatformService writePlatformService;
    @Autowired
    public UpdateInterestRateChartCommandHandler(final InterestRateChartWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.update(command.entityId(), command);
    }
}
