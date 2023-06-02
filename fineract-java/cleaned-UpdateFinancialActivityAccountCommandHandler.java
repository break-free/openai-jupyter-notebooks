
package org.apache.fineract.accounting.financialactivityaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.financialactivityaccount.service.FinancialActivityAccountWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "FINANCIALACTIVITYACCOUNT", action = "UPDATE")
@RequiredArgsConstructor
public class UpdateFinancialActivityAccountCommandHandler implements NewCommandSourceHandler {
    private final FinancialActivityAccountWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateGLAccountActivityMapping(command.entityId(), command);
    }
}
