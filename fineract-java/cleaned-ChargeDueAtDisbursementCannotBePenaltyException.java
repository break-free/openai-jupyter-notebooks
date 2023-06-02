
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ChargeDueAtDisbursementCannotBePenaltyException extends AbstractPlatformDomainRuleException {
    public ChargeDueAtDisbursementCannotBePenaltyException(final String name) {
        super("error.msg.charge.due.at.disbursement.cannot.be.penalty", "Charge '" + name + "' is invalid.", name, name);
    }
}
