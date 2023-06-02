
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.service.DepositApplicationProcessWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "FIXEDDEPOSITACCOUNT", action = "APPROVALUNDO")
public class FixedDepositAccountApplicationApprovalUndoCommandHandler implements NewCommandSourceHandler {
    private final DepositApplicationProcessWritePlatformService depositAccountWritePlatformService;
    @Autowired
    public FixedDepositAccountApplicationApprovalUndoCommandHandler(
            final DepositApplicationProcessWritePlatformService depositAccountWritePlatformService) {
        this.depositAccountWritePlatformService = depositAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.depositAccountWritePlatformService.undoApplicationApproval(command.entityId(), command,
                DepositAccountType.FIXED_DEPOSIT);
    }
}
