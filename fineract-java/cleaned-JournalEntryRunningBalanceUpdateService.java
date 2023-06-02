
package org.apache.fineract.accounting.journalentry.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface JournalEntryRunningBalanceUpdateService {
    void updateRunningBalance();
    CommandProcessingResult updateOfficeRunningBalance(JsonCommand command);
}
