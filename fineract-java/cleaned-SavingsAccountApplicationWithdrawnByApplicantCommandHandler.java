
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
@CommandType(entity = "SAVINGSACCOUNT", action = "WITHDRAW")
public class SavingsAccountApplicationWithdrawnByApplicantCommandHandler implements NewCommandSourceHandler {
    private final SavingsApplicationProcessWritePlatformService writePlatformService;
    @Autowired
    public SavingsAccountApplicationWithdrawnByApplicantCommandHandler(
            final SavingsApplicationProcessWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.applicantWithdrawsFromApplication(command.entityId(), command);
    }
}
