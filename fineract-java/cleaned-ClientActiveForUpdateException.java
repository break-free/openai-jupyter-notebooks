
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientActiveForUpdateException extends AbstractPlatformDomainRuleException {
    public ClientActiveForUpdateException(final Long clientId, final String parameterName) {
        super("error.msg.client.active.for.update.parameter." + parameterName,
                "The Client with id `" + clientId + "` is active,can't update parameter " + parameterName, clientId, parameterName);
    }
}
