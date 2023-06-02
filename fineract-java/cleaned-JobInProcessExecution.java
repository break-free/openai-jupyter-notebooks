
package org.apache.fineract.infrastructure.jobs.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class JobInProcessExecution extends AbstractPlatformResourceNotFoundException {
    public JobInProcessExecution(final String identifier) {
        super("error.msg.sheduler.job.inprogress", "job execution is in process for " + identifier, identifier);
    }
}
