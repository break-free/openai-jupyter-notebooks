
package org.apache.fineract.infrastructure.reportmailingjob.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
@SuppressWarnings("serial")
public class ReportMailingJobConfigurationNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ReportMailingJobConfigurationNotFoundException(final String name) {
        super("error.msg.report.mailing.job.configuration.name.invalid",
                "Report mailing job configuration with name " + name + " does not exist", name);
    }
    public ReportMailingJobConfigurationNotFoundException(String name, EmptyResultDataAccessException ex) {
        super("error.msg.report.mailing.job.configuration.name.invalid",
                "Report mailing job configuration with name " + name + " does not exist", name, ex);
    }
}
