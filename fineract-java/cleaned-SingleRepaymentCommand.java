
package org.apache.fineract.portfolio.collectionsheet.command;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
public class SingleRepaymentCommand {
    private final Long loanId;
    private final BigDecimal transactionAmount;
    private final LocalDate transactionDate;
    private final PaymentDetail paymentDetail;
    public SingleRepaymentCommand(final Long loanId, final BigDecimal transactionAmount, final LocalDate transactionDate,
            final PaymentDetail paymentDetail) {
        this.loanId = loanId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.paymentDetail = paymentDetail;
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
    public PaymentDetail getPaymentDetail() {
        return this.paymentDetail;
    }
}
