
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
@CommandType(entity = "SAVINGSACCOUNT", action = "RELEASEAMOUNT")
public class ReleaseAmountSavingsAccountCommandHandler implements NewCommandSourceHandler {
    private final SavingsAccountWritePlatformService writePlatformService;
    @Autowired
    public ReleaseAmountSavingsAccountCommandHandler(final SavingsAccountWritePlatformService savingAccountWritePlatformService) {
        this.writePlatformService = savingAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        final Long transactionId = Long.valueOf(command.getTransactionId());
        return this.writePlatformService.releaseAmount(command.getSavingsId(), transactionId);
    }
}
