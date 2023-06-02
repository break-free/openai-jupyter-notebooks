
package org.apache.fineract.portfolio.loanproduct.domain;
import java.math.BigDecimal;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.loanproduct.LoanProductConstants;
@Embeddable
public class LoanProductTrancheDetails {
    @Column(name = "allow_multiple_disbursals")
    private boolean multiDisburseLoan;
    @Column(name = "max_disbursals", nullable = true)
    private Integer maxTrancheCount;
    @Column(name = "max_outstanding_loan_balance", scale = 6, precision = 19, nullable = true)
    private BigDecimal outstandingLoanBalance;
    protected LoanProductTrancheDetails() {
    }
    public LoanProductTrancheDetails(final boolean multiDisburseLoan, final Integer maxTrancheCount,
            final BigDecimal outstandingLoanBalance) {
        this.multiDisburseLoan = multiDisburseLoan;
        this.maxTrancheCount = maxTrancheCount;
        this.outstandingLoanBalance = outstandingLoanBalance;
    }
    public void update(final JsonCommand command, final Map<String, Object> actualChanges, final String localeAsInput) {
        if (command.isChangeInBooleanParameterNamed(LoanProductConstants.MULTI_DISBURSE_LOAN_PARAMETER_NAME, this.multiDisburseLoan)) {
            final boolean newValue = command.booleanPrimitiveValueOfParameterNamed(LoanProductConstants.MULTI_DISBURSE_LOAN_PARAMETER_NAME);
            actualChanges.put(LoanProductConstants.MULTI_DISBURSE_LOAN_PARAMETER_NAME, newValue);
            this.multiDisburseLoan = newValue;
        }
        if (this.multiDisburseLoan) {
            if (command.isChangeInIntegerParameterNamed(LoanProductConstants.MAX_TRANCHE_COUNT_PARAMETER_NAME, this.maxTrancheCount)) {
                final Integer newValue = command.integerValueOfParameterNamed(LoanProductConstants.MAX_TRANCHE_COUNT_PARAMETER_NAME);
                actualChanges.put(LoanProductConstants.MAX_TRANCHE_COUNT_PARAMETER_NAME, newValue);
                this.maxTrancheCount = newValue;
            }
            if (command.isChangeInBigDecimalParameterNamed(LoanProductConstants.OUTSTANDING_LOAN_BALANCE_PARAMETER_NAME,
                    this.outstandingLoanBalance)) {
                final BigDecimal newValue = command
                        .bigDecimalValueOfParameterNamed(LoanProductConstants.OUTSTANDING_LOAN_BALANCE_PARAMETER_NAME);
                actualChanges.put(LoanProductConstants.OUTSTANDING_LOAN_BALANCE_PARAMETER_NAME, newValue);
                actualChanges.put(LoanProductConstants.OUTSTANDING_LOAN_BALANCE_PARAMETER_NAME, localeAsInput);
                this.outstandingLoanBalance = newValue;
            }
        } else {
            this.maxTrancheCount = null;
            this.outstandingLoanBalance = null;
        }
    }
    public boolean isMultiDisburseLoan() {
        return this.multiDisburseLoan;
    }
    public BigDecimal outstandingLoanBalance() {
        return this.outstandingLoanBalance;
    }
    public Integer maxTrancheCount() {
        return this.maxTrancheCount;
    }
}
