
package org.apache.fineract.portfolio.loanproduct.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanproduct.service.LoanProductWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "LOANPRODUCT", action = "UPDATE")
public class UpdateLoanProductCommandHandler implements NewCommandSourceHandler {
    private final LoanProductWritePlatformService writePlatformService;
    @Autowired
    public UpdateLoanProductCommandHandler(final LoanProductWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateLoanProduct(command.entityId(), command);
    }
}
