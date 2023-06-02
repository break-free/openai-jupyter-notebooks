
package org.apache.fineract.infrastructure.sms.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class SmsNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SmsNotFoundException(final Long resourceId) {
        super("error.msg.sms.identifier.not.found", "SMS with identifier `" + resourceId + "` does not exist", resourceId);
    }
    public SmsNotFoundException(Long resourceId, EmptyResultDataAccessException e) {
        super("error.msg.sms.identifier.not.found", "SMS with identifier `" + resourceId + "` does not exist", resourceId, e);
    }
}
