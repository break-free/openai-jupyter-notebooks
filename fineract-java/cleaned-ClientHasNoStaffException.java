
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ClientHasNoStaffException extends AbstractPlatformResourceNotFoundException {
    public ClientHasNoStaffException(final Long clientId) {
        super("error.msg.client.has.no.staff", "Client with identifier " + clientId + " does not have staff", clientId);
    }
}
