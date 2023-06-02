
package org.apache.fineract.portfolio.collectionsheet.command;
import java.time.LocalDate;
public class CollectionSheetBulkDisbursalCommand {
    private final String note;
    private final LocalDate transactionDate;
    private final SingleDisbursalCommand[] disburseTransactions;
    public CollectionSheetBulkDisbursalCommand(final String note, final LocalDate transactionDate,
            final SingleDisbursalCommand[] disburseTransactions) {
        this.note = note;
        this.transactionDate = transactionDate;
        this.disburseTransactions = disburseTransactions;
    }
    public String getNote() {
        return this.note;
    }
    public SingleDisbursalCommand[] getDisburseTransactions() {
        return this.disburseTransactions;
    }
    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }
}
