
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
@SuppressWarnings("serial")
public class CashierDateRangeOutOfTellerDateRangeException extends AbstractPlatformDomainRuleException {
    public CashierDateRangeOutOfTellerDateRangeException() {
        super("cashier.date.range.out.of.teller.date.range.exception", "Cashier date range should be in teller date range.");
    }
}
