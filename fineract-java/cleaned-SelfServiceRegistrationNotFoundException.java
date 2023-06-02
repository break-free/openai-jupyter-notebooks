
package org.apache.fineract.portfolio.self.registration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class SelfServiceRegistrationNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SelfServiceRegistrationNotFoundException(Long requestId, String authenticationToken) {
        super("error.msg.self.service.registration.not.found",
                "Self service registration not found with request id : " + requestId + " and authentication token :" + authenticationToken);
    }
}
