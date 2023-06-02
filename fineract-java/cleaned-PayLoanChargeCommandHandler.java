
package org.apache.fineract.portfolio.loanaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@CommandType(entity = "LOANCHARGE", action = "PAY")
public class PayLoanChargeCommandHandler implements NewCommandSourceHandler {
    private final LoanWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.payLoanCharge(command.getLoanId(), command.entityId(), command, command.entityId() == null);
    }
}
