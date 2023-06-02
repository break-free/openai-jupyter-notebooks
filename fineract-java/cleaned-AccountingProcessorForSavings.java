
package org.apache.fineract.accounting.journalentry.service;
import org.apache.fineract.accounting.journalentry.data.SavingsDTO;
public interface AccountingProcessorForSavings {
    void createJournalEntriesForSavings(SavingsDTO savingsDTO);
}
