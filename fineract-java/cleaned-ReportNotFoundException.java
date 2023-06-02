
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
@SuppressWarnings("serial")
public class ReportNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ReportNotFoundException(final String reportName) {
        super("error.msg.report.name.not.found", "Reporting meta-data entry not found.", "Report Name: " + reportName);
    }
    public ReportNotFoundException(final Long id) {
        super("error.msg.report.parameter.id.invalid", "Report Parameter with identifier " + id + " does not exist", id);
    }
}
