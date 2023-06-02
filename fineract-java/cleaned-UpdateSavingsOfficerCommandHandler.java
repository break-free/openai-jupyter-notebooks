
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.SavingsAccountWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "SAVINGSACCOUNT", action = "UPDATESAVINGSOFFICER")
public class UpdateSavingsOfficerCommandHandler implements NewCommandSourceHandler {
    private final SavingsAccountWritePlatformService savingsWritePlatformService;
    @Autowired
    public UpdateSavingsOfficerCommandHandler(final SavingsAccountWritePlatformService savingAccountWritePlatformService) {
        this.savingsWritePlatformService = savingAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.savingsWritePlatformService.assignFieldOfficer(command.entityId(), command);
    }
}
