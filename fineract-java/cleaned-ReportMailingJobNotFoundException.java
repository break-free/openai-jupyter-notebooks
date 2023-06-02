
package org.apache.fineract.infrastructure.reportmailingjob.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
@SuppressWarnings("serial")
public class ReportMailingJobNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ReportMailingJobNotFoundException(final Long reportMailingJobId) {
        super("error.msg.report.mailing.job.id.invalid", "Report mailing job with identifier " + reportMailingJobId + " does not exist",
                reportMailingJobId);
    }
    public ReportMailingJobNotFoundException(Long reportMailingJobId, EmptyResultDataAccessException ex) {
        super("error.msg.report.mailing.job.id.invalid", "Report mailing job with identifier " + reportMailingJobId + " does not exist",
                reportMailingJobId, ex);
    }
}
