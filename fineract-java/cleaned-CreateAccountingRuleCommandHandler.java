
package org.apache.fineract.accounting.rule.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.rule.service.AccountingRuleWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ACCOUNTINGRULE", action = "CREATE")
@RequiredArgsConstructor
public class CreateAccountingRuleCommandHandler implements NewCommandSourceHandler {
    private final AccountingRuleWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createAccountingRule(command);
    }
}
