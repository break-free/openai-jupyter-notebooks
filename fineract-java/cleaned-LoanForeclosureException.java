
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanForeclosureException extends AbstractPlatformDomainRuleException {
    public LoanForeclosureException(final String errorCode, final String errorMessage, final Object... defaultUserMessageArgs) {
        super(errorCode, errorMessage, defaultUserMessageArgs);
    }
}
