
package org.apache.fineract.accounting.journalentry.service;
import org.apache.fineract.accounting.journalentry.data.ClientTransactionDTO;
public interface AccountingProcessorForClientTransactions {
    void createJournalEntriesForClientTransaction(ClientTransactionDTO clientTransactionDTO);
}
