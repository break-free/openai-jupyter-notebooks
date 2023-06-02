
package org.apache.fineract.accounting.accrual.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.accrual.service.AccrualAccountingWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PERIODICACCRUALACCOUNTING", action = "EXECUTE")
@RequiredArgsConstructor
public class ExecutePeriodicAccrualCommandHandler implements NewCommandSourceHandler {
    private final AccrualAccountingWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.executeLoansPeriodicAccrual(command);
    }
}
