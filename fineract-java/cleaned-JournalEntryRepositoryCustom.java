
package org.apache.fineract.accounting.journalentry.domain;
import java.util.List;
public interface JournalEntryRepositoryCustom {
    List<JournalEntry> findFirstJournalEntryForAccount(long glAccountId);
}
