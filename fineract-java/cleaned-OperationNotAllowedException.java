
package org.apache.fineract.infrastructure.jobs.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformServiceUnavailableException;
public class OperationNotAllowedException extends AbstractPlatformServiceUnavailableException {
    public OperationNotAllowedException(final String jobNames) {
        super("error.msg.sheduler.job.currently.running",
                "Execution is in-process for jobs " + jobNames + "...., so update operations are not allowed at this moment", jobNames);
    }
}
