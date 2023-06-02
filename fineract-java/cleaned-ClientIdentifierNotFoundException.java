
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ClientIdentifierNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ClientIdentifierNotFoundException(final Long id) {
        super("error.msg.clientIdentifier.id.invalid", "Client Identifier with the primary key " + id + " does not exist", id);
    }
    public ClientIdentifierNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.clientIdentifier.id.invalid", "Client Identifier with the primary key " + id + " does not exist", id, e);
    }
}
