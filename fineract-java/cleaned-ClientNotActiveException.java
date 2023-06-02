
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientNotActiveException extends AbstractPlatformDomainRuleException {
    public ClientNotActiveException(final Long clientId) {
        super("error.msg.client.not.active.exception", "The Client with id `" + clientId + "` is not active", clientId);
    }
}
