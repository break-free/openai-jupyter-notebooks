
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
@CommandType(entity = "GUARANTOR", action = "DELETE")
public class DeleteGuarantorCommandHandler implements NewCommandSourceHandler {
    private final GuarantorWritePlatformService guarantorWritePlatformService;
    @Autowired
    public DeleteGuarantorCommandHandler(final GuarantorWritePlatformService guarantorWritePlatformService) {
        this.guarantorWritePlatformService = guarantorWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.guarantorWritePlatformService.removeGuarantor(command.getLoanId(), command.entityId(), command.subentityId());
    }
}
