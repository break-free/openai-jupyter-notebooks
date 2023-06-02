
package org.apache.fineract.portfolio.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DifferentCurrenciesException extends AbstractPlatformDomainRuleException {
    public DifferentCurrenciesException(final String currency1, final String currency2) {
        super("error.msg.accounttransfer.different.currencies", "Trying to transfer from " + currency1 + " to " + currency2);
    }
}
