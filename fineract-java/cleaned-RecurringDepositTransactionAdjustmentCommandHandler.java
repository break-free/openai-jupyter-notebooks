
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.DepositAccountWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "RECURRINGDEPOSITACCOUNT", action = "ADJUSTTRANSACTION")
public class RecurringDepositTransactionAdjustmentCommandHandler implements NewCommandSourceHandler {
    private final DepositAccountWritePlatformService depositAccountWritePlatformService;
    @Autowired
    public RecurringDepositTransactionAdjustmentCommandHandler(
            final DepositAccountWritePlatformService depositAccountWritePlatformService) {
        this.depositAccountWritePlatformService = depositAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        final Long transactionId = Long.valueOf(command.getTransactionId());
        return this.depositAccountWritePlatformService.adjustRDTransaction(command.entityId(), transactionId, command);
    }
}
