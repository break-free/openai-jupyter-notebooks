
package org.apache.fineract.infrastructure.jobs.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class JobNotFoundException extends AbstractPlatformResourceNotFoundException {
    public JobNotFoundException(final String identifier) {
        super("error.msg.sheduler.job.id.invalid", "Job with identifier " + identifier + " does not exist", identifier);
    }
    public JobNotFoundException(String identifier, EmptyResultDataAccessException e) {
        super("error.msg.sheduler.job.id.invalid", "Job with identifier " + identifier + " does not exist", identifier, e);
    }
}
