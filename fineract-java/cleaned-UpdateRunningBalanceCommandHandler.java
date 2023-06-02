
package org.apache.fineract.accounting.journalentry.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.journalentry.service.JournalEntryRunningBalanceUpdateService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "JOURNALENTRY", action = "UPDATERUNNINGBALANCE")
@RequiredArgsConstructor
public class UpdateRunningBalanceCommandHandler implements NewCommandSourceHandler {
    private final JournalEntryRunningBalanceUpdateService journalEntryRunningBalanceUpdateService;
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return journalEntryRunningBalanceUpdateService.updateOfficeRunningBalance(command);
    }
}
