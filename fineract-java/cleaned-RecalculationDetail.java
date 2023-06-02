
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.time.LocalDate;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class RecalculationDetail {
    private LocalDate transactionDate;
    private boolean isProcessed;
    private LoanTransaction transaction;
    public RecalculationDetail(final LocalDate transactionDate, final LoanTransaction transaction) {
        this.transactionDate = transactionDate;
        this.transaction = transaction;
    }
    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }
    public LoanTransaction getTransaction() {
        return this.transaction;
    }
    public boolean isProcessed() {
        return this.isProcessed;
    }
    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
}
