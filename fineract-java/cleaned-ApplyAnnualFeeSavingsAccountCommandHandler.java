
package org.apache.fineract.portfolio.savings.handler;
import java.time.LocalDate;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.SavingsAccountWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "SAVINGSACCOUNT", action = "APPLYANNUALFEE")
public class ApplyAnnualFeeSavingsAccountCommandHandler implements NewCommandSourceHandler {
    @SuppressWarnings("unused")
    private final SavingsAccountWritePlatformService writePlatformService;
    @Autowired
    public ApplyAnnualFeeSavingsAccountCommandHandler(final SavingsAccountWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        @SuppressWarnings("unused")
        final LocalDate annualFeeTransactionDate = command.localDateValueOfParameterNamed("annualFeeTransactionDate");
        return null;
    }
}
