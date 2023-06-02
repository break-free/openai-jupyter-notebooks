
package org.apache.fineract.infrastructure.security.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ForcePasswordResetException extends AbstractPlatformDomainRuleException {
    public ForcePasswordResetException() {
        super("error.msg.password.reset.days.value.must.be.greater.than.zero",
                "For enabling 'Force Password Reset Days' configuration , the value (number of days after which a user is forced to reset his password) must be set to a number greater than 0.");
    }
}
