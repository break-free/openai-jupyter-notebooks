
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ReportParameterNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ReportParameterNotFoundException(final Long id) {
        super("error.msg.report.parameter.id.invalid", "Report Parameter with identifier " + id + " does not exist", id);
    }
}
