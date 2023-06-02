
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.service.LoanRescheduleRequestWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "RESCHEDULELOAN", action = "REJECT")
public class RejectLoanRescheduleRequestCommandHandler implements NewCommandSourceHandler {
    private final LoanRescheduleRequestWritePlatformService loanRescheduleRequestWritePlatformService;
    @Autowired
    public RejectLoanRescheduleRequestCommandHandler(LoanRescheduleRequestWritePlatformService loanRescheduleRequestWritePlatformService) {
        this.loanRescheduleRequestWritePlatformService = loanRescheduleRequestWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.loanRescheduleRequestWritePlatformService.reject(jsonCommand);
    }
}
