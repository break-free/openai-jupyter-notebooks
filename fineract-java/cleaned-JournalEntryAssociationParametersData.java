
package org.apache.fineract.accounting.journalentry.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class JournalEntryAssociationParametersData {
    private final boolean transactionDetailsRequired;
    private final boolean runningBalanceRequired;
    public JournalEntryAssociationParametersData() {
        this.transactionDetailsRequired = false;
        this.runningBalanceRequired = false;
    }
}
