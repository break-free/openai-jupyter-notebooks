
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.SavingsProductWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "SAVINGSPRODUCT", action = "UPDATE")
public class UpdateSavingsProductCommandHandler implements NewCommandSourceHandler {
    private final SavingsProductWritePlatformService savingProductWritePlatformService;
    @Autowired
    public UpdateSavingsProductCommandHandler(final SavingsProductWritePlatformService savingProductWritePlatformService) {
        this.savingProductWritePlatformService = savingProductWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.savingProductWritePlatformService.update(command.entityId(), command);
    }
}
