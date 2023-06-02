
package org.apache.fineract.infrastructure.security.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class AccessTokenInvalidIException extends AbstractPlatformDomainRuleException {
    public AccessTokenInvalidIException() {
        super("error.msg.twofactor.access.token.invalid", "The provided access token is invalid");
    }
}
