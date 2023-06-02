
package org.apache.fineract.spm.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class SurveyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SurveyNotFoundException(final Long id) {
        super("error.msg.survey.id.notfound", "Survey with id " + id + " not found!", id);
    }
}
