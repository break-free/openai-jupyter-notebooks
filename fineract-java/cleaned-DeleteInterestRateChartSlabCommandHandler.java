
package org.apache.fineract.portfolio.interestratechart.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.interestratechart.service.InterestRateChartSlabWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "CHARTSLAB", action = "DELETE")
public class DeleteInterestRateChartSlabCommandHandler implements NewCommandSourceHandler {
    private final InterestRateChartSlabWritePlatformService writePlatformService;
    @Autowired
    public DeleteInterestRateChartSlabCommandHandler(final InterestRateChartSlabWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.deleteChartSlab(command.entityId(), command.subentityId());
    }
}
