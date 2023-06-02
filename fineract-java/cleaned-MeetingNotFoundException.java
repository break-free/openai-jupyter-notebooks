
package org.apache.fineract.portfolio.meeting.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class MeetingNotFoundException extends AbstractPlatformResourceNotFoundException {
    public MeetingNotFoundException(final Long id) {
        super("error.msg.meeting.id.invalid", "Meeting with identifier " + id + " does not exist", id);
    }
    public MeetingNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.meeting.id.invalid", "Meeting with identifier " + id + " does not exist", id, e);
    }
}
