
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientMustBePendingToBeDeletedException extends AbstractPlatformDomainRuleException {
    public ClientMustBePendingToBeDeletedException(final Long id) {
        super("error.msg.clients.cannot.be.deleted", "Client with identifier " + id + " cannot be deleted as it is not in Pending state.",
                id);
    }
}
