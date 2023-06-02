
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.SavingsApplicationProcessWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GSIMACCOUNT", action = "APPROVE")
public class GSIMApplicationApprovalCommandHandler implements NewCommandSourceHandler {
    private final SavingsApplicationProcessWritePlatformService savingAccountWritePlatformService;
    @Autowired
    public GSIMApplicationApprovalCommandHandler(final SavingsApplicationProcessWritePlatformService savingAccountWritePlatformService) {
        this.savingAccountWritePlatformService = savingAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.savingAccountWritePlatformService.approveGSIMApplication(command.getSavingsId(), command);
    }
}
