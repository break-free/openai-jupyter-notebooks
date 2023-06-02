
package org.apache.fineract.infrastructure.businessdate.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class BusinessDateActionException extends AbstractPlatformDomainRuleException {
    public BusinessDateActionException(final String violationCode, String message) {
        super(violationCode, message, violationCode);
    }
}
