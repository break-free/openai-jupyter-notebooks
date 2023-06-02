
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ClientTransactionNotFoundException extends AbstractPlatformDomainRuleException {
    public ClientTransactionNotFoundException(final Long clientId, final Long transactionId) {
        super("error.msg.client.transaction.not.found.exception",
                "The Transaction with id `" + transactionId + "` does not exist for a Client with id `" + clientId, transactionId,
                clientId);
    }
    public ClientTransactionNotFoundException(Long clientId, Long transactionId, EmptyResultDataAccessException e) {
        super("error.msg.client.transaction.not.found.exception",
                "The Transaction with id `" + transactionId + "` does not exist for a Client with id `" + clientId, transactionId, clientId,
                e);
    }
}
