
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
@SuppressWarnings("serial")
public class CashierInsufficientAmountException extends AbstractPlatformDomainRuleException {
    public CashierInsufficientAmountException() {
        super("cashier.insufficient.amount.exception", "Cashier do not have sufficient amount for this transaction.");
    }
}
