
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientTransactionCannotBeUndoneException extends AbstractPlatformDomainRuleException {
    public ClientTransactionCannotBeUndoneException(final long clientId, final Long transactionId) {
        super("error.msg.clients.transaction.cannot.be.undone", "Client transaction with identifier " + transactionId
                + " for client with identifier " + clientId + " has already been reversed", transactionId);
    }
}
