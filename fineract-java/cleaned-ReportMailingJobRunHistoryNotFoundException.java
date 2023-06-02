
package org.apache.fineract.infrastructure.reportmailingjob.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
@SuppressWarnings("serial")
public class ReportMailingJobRunHistoryNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ReportMailingJobRunHistoryNotFoundException(final Long reportMailingJobId) {
        super("error.msg.report.mailing.job.run.history.invalid",
                "Report mailing job run history with job identifier " + reportMailingJobId + " does not exist", reportMailingJobId);
    }
}
