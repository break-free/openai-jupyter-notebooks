
package org.apache.fineract.infrastructure.jobs.exception;
import java.util.List;
import org.apache.fineract.infrastructure.core.exception.MultiException;
public class JobExecutionException extends MultiException {
    public JobExecutionException(List<Throwable> problems) {
        super(problems);
    }
    public JobExecutionException(MultiException multiException) {
        super(multiException.getCauses());
    }
}
