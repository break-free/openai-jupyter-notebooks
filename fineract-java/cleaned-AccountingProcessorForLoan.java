
package org.apache.fineract.accounting.journalentry.service;
import org.apache.fineract.accounting.journalentry.data.LoanDTO;
public interface AccountingProcessorForLoan {
    void createJournalEntriesForLoan(LoanDTO loanDTO);
}
