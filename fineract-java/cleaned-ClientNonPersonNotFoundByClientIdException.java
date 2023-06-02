
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ClientNonPersonNotFoundByClientIdException extends AbstractPlatformResourceNotFoundException {
    public ClientNonPersonNotFoundByClientIdException(final Long id) {
        super("error.msg.clientnonperson.id.invalid", "ClientNonPerson with client identifier " + id + " does not exist", id);
    }
}
