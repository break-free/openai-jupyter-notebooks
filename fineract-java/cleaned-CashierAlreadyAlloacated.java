
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
@SuppressWarnings("serial")
public class CashierAlreadyAlloacated extends AbstractPlatformDomainRuleException {
    public CashierAlreadyAlloacated() {
        super("cashier.already.allocated.for.given.data.and.time.exception", "Cashier already allocated for given date and time range.");
    }
}
