
package org.apache.fineract.infrastructure.accountnumberformat.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.accountnumberformat.service.AccountNumberFormatWritePlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ACCOUNTNUMBERFORMAT", action = "UPDATE")
public class UpdateAccountNumberFormatCommandHandler implements NewCommandSourceHandler {
    private final AccountNumberFormatWritePlatformService accountNumberFormatWritePlatformService;
    @Autowired
    public UpdateAccountNumberFormatCommandHandler(final AccountNumberFormatWritePlatformService accountNumberFormatWritePlatformService) {
        this.accountNumberFormatWritePlatformService = accountNumberFormatWritePlatformService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.accountNumberFormatWritePlatformService.updateAccountNumberFormat(command.entityId(), command);
    }
}
