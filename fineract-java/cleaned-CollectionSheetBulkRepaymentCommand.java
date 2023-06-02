
package org.apache.fineract.portfolio.collectionsheet.command;
import java.time.LocalDate;
public class CollectionSheetBulkRepaymentCommand {
    private final String note;
    private final LocalDate transactionDate;
    private final SingleRepaymentCommand[] repaymentTransactions;
    public CollectionSheetBulkRepaymentCommand(final String note, final LocalDate transactionDate,
            final SingleRepaymentCommand[] repaymentTransactions) {
        this.note = note;
        this.transactionDate = transactionDate;
        this.repaymentTransactions = repaymentTransactions;
    }
    public String getNote() {
        return this.note;
    }
    public SingleRepaymentCommand[] getLoanTransactions() {
        return this.repaymentTransactions;
    }
    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }
}
