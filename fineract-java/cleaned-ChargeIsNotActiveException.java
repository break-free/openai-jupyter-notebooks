
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ChargeIsNotActiveException extends AbstractPlatformDomainRuleException {
    public ChargeIsNotActiveException(final Long id, final String name) {
        super("error.msg.charge.is.not.active", "Charge '" + name + "' with identifier " + id + " is not active", name, id);
    }
}
