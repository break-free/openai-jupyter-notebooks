
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ClientChargeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ClientChargeNotFoundException(final Long id) {
        super("error.msg.client.charge.id.invalid", "Client charge with identifier " + id + " does not exist", id);
    }
    public ClientChargeNotFoundException(final Long id, final Long clientId) {
        super("error.msg.client.charge.id.invalid.for.given.client",
                "Client charge with identifier " + id + " does not exist for client with id " + clientId, id, clientId);
    }
    public ClientChargeNotFoundException(Long id, Long clientId, EmptyResultDataAccessException e) {
        super("error.msg.client.charge.id.invalid.for.given.client",
                "Client charge with identifier " + id + " does not exist for client with id " + clientId, id, clientId, e);
    }
}
