
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ClientNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ClientNotFoundException(final Long id) {
        super("error.msg.client.id.invalid", "Client with identifier " + id + " does not exist", id);
    }
    public ClientNotFoundException() {
        super("error.msg.client.not.found.with.basic.details", "Client not found with basic details.");
    }
    public ClientNotFoundException(String accountNumber) {
        super("error.msg.client.not.found.with.account.number", "Client not found with account number " + accountNumber + ".");
    }
    public ClientNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.client.id.invalid", "Client with identifier " + id + " does not exist", id, e);
    }
}
