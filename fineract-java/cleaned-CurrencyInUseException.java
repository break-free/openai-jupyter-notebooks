
package org.apache.fineract.organisation.monetary.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CurrencyInUseException extends AbstractPlatformDomainRuleException {
    public CurrencyInUseException(final String currencyCode) {
        super("error.msg.currency.currencyCode.inUse",
                "Cannot remove currency with identifier " + currencyCode + " while it is still in use", currencyCode);
    }
}
