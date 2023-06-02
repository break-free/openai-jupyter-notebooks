
package org.apache.fineract.portfolio.collectionsheet.command;
import java.math.BigDecimal;
import java.time.LocalDate;
public class SingleDisbursalCommand {
    private final Long loanId;
    private final BigDecimal transactionAmount;
    private final LocalDate transactionDate;
    public SingleDisbursalCommand(final Long loanId, final BigDecimal transactionAmount, final LocalDate transactionDate) {
        this.loanId = loanId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }
    public Long getLoanId() {
        return this.loanId;
    }
    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }
    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }
}
