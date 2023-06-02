
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class EmailNotFoundException extends AbstractPlatformResourceNotFoundException {
    public EmailNotFoundException(final Long resourceId) {
        super("error.msg.email.identifier.not.found", "Email with identifier `" + resourceId + "` does not exist", resourceId);
    }
    public EmailNotFoundException(Long resourceId, EmptyResultDataAccessException e) {
        super("error.msg.email.identifier.not.found", "Email with identifier `" + resourceId + "` does not exist", resourceId, e);
    }
}
