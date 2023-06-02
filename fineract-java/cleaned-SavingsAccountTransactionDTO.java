
package org.apache.fineract.portfolio.savings.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
import org.apache.fineract.useradministration.domain.AppUser;
public class SavingsAccountTransactionDTO {
    private final DateTimeFormatter formatter;
    private final LocalDate transactionDate;
    private final BigDecimal transactionAmount;
    private final PaymentDetail paymentDetail;
    private final LocalDateTime createdDate;
    private final Long savingsAccountId;
    private final AppUser appUser;
    private final Integer depositAccountType;
    public SavingsAccountTransactionDTO(final DateTimeFormatter formatter, final LocalDate transactionDate,
            final BigDecimal transactionAmount, final PaymentDetail paymentDetail, final LocalDateTime createdDate, final AppUser appUser,
            final Integer depositAccountType) {
        this.formatter = formatter;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.paymentDetail = paymentDetail;
        this.createdDate = createdDate;
        this.savingsAccountId = null;
        this.appUser = appUser;
        this.depositAccountType = depositAccountType;
    }
    public SavingsAccountTransactionDTO(DateTimeFormatter formatter, LocalDate transactionDate, BigDecimal transactionAmount,
            PaymentDetail paymentDetail, LocalDateTime createdDate, Long savingsAccountId, AppUser appUser,
            final Integer depositAccountType) {
        this.formatter = formatter;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.paymentDetail = paymentDetail;
        this.createdDate = createdDate;
        this.savingsAccountId = savingsAccountId;
        this.appUser = appUser;
        this.depositAccountType = depositAccountType;
    }
    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }
    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }
    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }
    public PaymentDetail getPaymentDetail() {
        return this.paymentDetail;
    }
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }
    public Long getSavingsAccountId() {
        return this.savingsAccountId;
    }
    public AppUser getAppUser() {
        return this.appUser;
    }
    public Integer getAccountType() {
        return this.depositAccountType;
    }
}
