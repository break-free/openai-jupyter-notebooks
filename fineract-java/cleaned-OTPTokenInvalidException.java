
package org.apache.fineract.infrastructure.security.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class OTPTokenInvalidException extends AbstractPlatformDomainRuleException {
    public OTPTokenInvalidException() {
        super("error.msg.twofactor.otp.token.invalid", "The provided one time token is invalid");
    }
}
