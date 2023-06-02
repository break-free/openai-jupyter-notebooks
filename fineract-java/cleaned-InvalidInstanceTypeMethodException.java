
package org.apache.fineract.infrastructure.security.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidInstanceTypeMethodException extends AbstractPlatformDomainRuleException {
    public InvalidInstanceTypeMethodException(final String method) {
        super("error.msg.invalid.method.for.instance.type", "Method Not Allowed " + method + " for the instance type");
    }
}
