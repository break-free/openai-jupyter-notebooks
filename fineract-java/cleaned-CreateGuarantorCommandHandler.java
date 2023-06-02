
package org.apache.fineract.portfolio.loanaccount.guarantor.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.guarantor.service.GuarantorWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GUARANTOR", action = "CREATE")
public class CreateGuarantorCommandHandler implements NewCommandSourceHandler {
    private final GuarantorWritePlatformService writePlatformService;
    @Autowired
    public CreateGuarantorCommandHandler(final GuarantorWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createGuarantor(command.getLoanId(), command);
    }
}
