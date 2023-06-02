
package org.apache.fineract.accounting.journalentry.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import org.apache.fineract.accounting.provisioning.domain.ProvisioningEntry;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface JournalEntryWritePlatformService {
    CommandProcessingResult createJournalEntry(JsonCommand command);
    CommandProcessingResult revertJournalEntry(JsonCommand command);
    void createJournalEntriesForLoan(Map<String, Object> accountingBridgeData);
    void createJournalEntriesForSavings(Map<String, Object> accountingBridgeData);
    void createJournalEntriesForClientTransactions(Map<String, Object> accountingBridgeData);
    CommandProcessingResult defineOpeningBalance(JsonCommand command);
    String revertProvisioningJournalEntries(LocalDate reversalTransactionDate, Long entityId, Integer entityType);
    String createProvisioningJournalEntries(ProvisioningEntry entry);
    void createJournalEntriesForShares(Map<String, Object> accountingBridgeData);
    void revertShareAccountJournalEntries(ArrayList<Long> transactionId, LocalDate transactionDate);
}
