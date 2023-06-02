
package org.apache.fineract.portfolio.loanaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@CommandType(entity = "LOAN", action = "RECOVERGUARANTEES")
public class RecoverFromGuarantorCommandHandler implements NewCommandSourceHandler {
    private final LoanWritePlatformService writePlatformService;
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.recoverFromGuarantor(command.getLoanId());
    }
}
