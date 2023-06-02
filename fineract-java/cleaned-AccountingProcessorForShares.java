
package org.apache.fineract.accounting.journalentry.service;
import org.apache.fineract.accounting.journalentry.data.SharesDTO;
public interface AccountingProcessorForShares {
    void createJournalEntriesForShares(SharesDTO sharesDTO);
}
