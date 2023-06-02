
package org.apache.fineract.portfolio.loanaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.loanschedule.service.LoanScheduleWritePlatformService;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@CommandType(entity = "LOAN", action = "CREATESCHEDULEEXCEPTIONS")
public class LoanScheduleCreateVariationCommandHandler implements NewCommandSourceHandler {
    private final LoanScheduleWritePlatformService loanScheduleWritePlatformService;
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.loanScheduleWritePlatformService.addLoanScheduleVariations(command.getLoanId(), command);
    }
}
