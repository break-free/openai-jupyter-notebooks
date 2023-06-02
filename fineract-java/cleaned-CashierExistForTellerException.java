
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CashierExistForTellerException extends AbstractPlatformDomainRuleException {
    private static final String ERROR_MESSAGE_CODE = "error.msg.cashier.is.associated.with.this.teller";
    private static final String DEFAULT_ERROR_MESSAGE = "Cannot delete teller, Cashier is associated for this teller ";
    public CashierExistForTellerException(Long tellerId) {
        super(ERROR_MESSAGE_CODE, DEFAULT_ERROR_MESSAGE, tellerId);
    }
}
