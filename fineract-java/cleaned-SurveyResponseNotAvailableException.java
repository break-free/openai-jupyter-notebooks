
package org.apache.fineract.spm.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SurveyResponseNotAvailableException extends AbstractPlatformDomainRuleException {
    public SurveyResponseNotAvailableException() {
        super("error.msg.no.survey.response", "No response available for survey.");
    }
}
