
package org.apache.fineract.portfolio.collateralmanagement.exception;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanCollateralAmountNotSufficientException extends AbstractPlatformDomainRuleException {
    public LoanCollateralAmountNotSufficientException(final BigDecimal amount) {
        super("error.msg.loan.disbursement.amount.not.sufficient",
                "The disbursement amount `" + amount + "`" + "` is invalid or insufficient to disburse the loan.", new Object[] { amount });
    }
}
