
package org.apache.fineract.accounting.journalentry.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.journalentry.service.JournalEntryWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "JOURNALENTRY", action = "CREATE")
@RequiredArgsConstructor
public class CreateJournalEntryCommandHandler implements NewCommandSourceHandler {
    private final JournalEntryWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createJournalEntry(command);
    }
}
