
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ClientNonPersonNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ClientNonPersonNotFoundException(final Long id) {
        super("error.msg.clientnonperson.id.invalid", "ClientNonPerson with identifier " + id + " does not exist", id);
    }
}
