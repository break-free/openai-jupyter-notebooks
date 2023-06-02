
package org.apache.fineract.infrastructure.core.exception;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
public class PlatformApiDataValidationException extends AbstractPlatformException {
    private final List<ApiParameterError> errors;
    public PlatformApiDataValidationException(List<ApiParameterError> errors) {
        super("validation.msg.validation.errors.exist", "Validation errors exist.");
        this.errors = errors;
    }
    public PlatformApiDataValidationException(final List<ApiParameterError> errors, Throwable cause) {
        super("validation.msg.validation.errors.exist", "Validation errors exist.", cause);
        this.errors = errors;
    }
    public PlatformApiDataValidationException(String globalisationMessageCode, String defaultUserMessage, List<ApiParameterError> errors) {
        super(globalisationMessageCode, defaultUserMessage);
        this.errors = errors;
    }
    public PlatformApiDataValidationException(String globalisationMessageCode, String defaultUserMessage, List<ApiParameterError> errors,
            Throwable cause) {
        super(globalisationMessageCode, defaultUserMessage, cause);
        this.errors = errors;
    }
    public List<ApiParameterError> getErrors() {
        return this.errors;
    }
    @Override
    public String toString() {
        return "PlatformApiDataValidationException{" + "errors=" + errors + '}';
    }
}
