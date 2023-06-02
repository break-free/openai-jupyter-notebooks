
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ChargeMustBePenaltyException extends AbstractPlatformDomainRuleException {
    public ChargeMustBePenaltyException(final String name) {
        super("error.msg.charge.must.be.penalty", "Charge '" + name + "' is invalid.", name, name);
    }
}
