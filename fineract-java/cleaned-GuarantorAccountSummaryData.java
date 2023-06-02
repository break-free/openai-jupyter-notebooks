
package org.apache.fineract.portfolio.accountdetails.data;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.data.LoanStatusEnumData;
@SuppressWarnings("unused")
public class GuarantorAccountSummaryData {
    private final Long id;
    private final String accountNo;
    private final String externalId;
    private final Long productId;
    private final String productName;
    private final String shortProductName;
    private final LoanStatusEnumData status;
    private final EnumOptionData loanType;
    private final Integer loanCycle;
    private final Boolean inArrears;
    private final BigDecimal originalLoan;
    private final BigDecimal loanBalance;
    private final BigDecimal amountPaid;
    private final Boolean isActive;
    private final String relationship;
    private final BigDecimal onHoldAmount;
    public GuarantorAccountSummaryData(final Long id, final String accountNo, final String externalId, final Long productId,
            final String loanProductName, final String shortLoanProductName, final LoanStatusEnumData loanStatus,
            final EnumOptionData loanType, final Integer loanCycle, final Boolean inArrears, final BigDecimal originalLoan,
            final BigDecimal loanBalance, final BigDecimal amountPaid, final Boolean isActive, final String relationship,
            final BigDecimal onHoldAmount) {
        this.id = id;
        this.accountNo = accountNo;
        this.externalId = externalId;
        this.productId = productId;
        this.productName = loanProductName;
        this.shortProductName = shortLoanProductName;
        this.status = loanStatus;
        this.loanType = loanType;
        this.loanCycle = loanCycle;
        this.inArrears = inArrears;
        this.loanBalance = loanBalance;
        this.originalLoan = originalLoan;
        this.amountPaid = amountPaid;
        this.isActive = isActive;
        this.relationship = relationship;
        this.onHoldAmount = onHoldAmount;
    }
}
