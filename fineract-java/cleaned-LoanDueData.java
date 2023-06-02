
package org.apache.fineract.portfolio.collectionsheet.data;
import java.math.BigDecimal;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public class LoanDueData {
    private final Long loanId;
    private final String accountId;
    private final Integer accountStatusId;
    private final String productShortName;
    private final Long productId;
    private final CurrencyData currency;
    private BigDecimal disbursementAmount = BigDecimal.ZERO;
    private BigDecimal principalDue = BigDecimal.ZERO;
    private BigDecimal principalPaid = BigDecimal.ZERO;
    private BigDecimal interestDue = BigDecimal.ZERO;
    private BigDecimal interestPaid = BigDecimal.ZERO;
    private BigDecimal chargesDue = BigDecimal.ZERO;
    private BigDecimal totalDue = BigDecimal.ZERO;
    private BigDecimal feeDue = BigDecimal.ZERO;
    private BigDecimal feePaid = BigDecimal.ZERO;
    public LoanDueData(final Long loanId, final String accountId, final Integer accountStatusId, final String productShortName,
            final Long productId, final CurrencyData currency, final BigDecimal disbursementAmount, final BigDecimal principalDue,
            final BigDecimal principalPaid, final BigDecimal interestDue, final BigDecimal interestPaid, final BigDecimal chargesDue,
            final BigDecimal feeDue, final BigDecimal feePaid) {
        this.loanId = loanId;
        this.accountId = accountId;
        this.accountStatusId = accountStatusId;
        this.productShortName = productShortName;
        this.productId = productId;
        this.currency = currency;
        this.disbursementAmount = disbursementAmount;
        this.principalDue = principalDue;
        this.principalPaid = principalPaid;
        this.interestDue = interestDue;
        this.interestPaid = interestPaid;
        this.chargesDue = chargesDue;
        this.feeDue = feeDue;
        this.feePaid = feePaid;
        this.totalDue = this.totalDue.add(principalDue).add(interestDue).add(feeDue);
    }
    public Long getLoanId() {
        return this.loanId;
    }
    public String getAccountId() {
        return this.accountId;
    }
    public Integer getAccountStatusId() {
        return this.accountStatusId;
    }
    public String getProductShortName() {
        return this.productShortName;
    }
    public Long getProductId() {
        return this.productId;
    }
    public CurrencyData getCurrency() {
        return this.currency;
    }
    public BigDecimal getDisbursementAmount() {
        return this.disbursementAmount;
    }
    public BigDecimal getPrincipalDue() {
        return this.principalDue;
    }
    public BigDecimal getPrincipalPaid() {
        return this.principalPaid;
    }
    public BigDecimal getInterestDue() {
        return this.interestDue;
    }
    public BigDecimal getInterestPaid() {
        return this.interestPaid;
    }
    public BigDecimal getChargesDue() {
        return this.chargesDue;
    }
    public BigDecimal getFeeDue() {
        return this.feeDue;
    }
    public BigDecimal getFeePaid() {
        return this.feePaid;
    }
}
