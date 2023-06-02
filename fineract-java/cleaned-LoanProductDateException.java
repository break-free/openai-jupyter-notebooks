
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanProductDateException extends AbstractPlatformDomainRuleException {
    public LoanProductDateException(final Object... defaultUserMessageArgs) {
        super("error.msg.loan.product.close.date.cannot.be.before.start.date.close.date",
                "Loan product close date cannot be before the start date", defaultUserMessageArgs);
    }
}
