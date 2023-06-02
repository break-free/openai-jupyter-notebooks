
package org.apache.fineract.accounting.closure.command;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.closure.api.GLClosureJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
@RequiredArgsConstructor
@Getter
public class GLClosureCommand {
    private final Long id;
    private final Long officeId;
    private final LocalDate closingDate;
    private final String comments;
    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("GLClosure");
        baseDataValidator.reset().parameter(GLClosureJsonInputParams.CLOSING_DATE.getValue()).value(this.closingDate).notBlank();
        baseDataValidator.reset().parameter(GLClosureJsonInputParams.OFFICE_ID.getValue()).value(this.officeId).notNull()
                .integerGreaterThanZero();
        baseDataValidator.reset().parameter(GLClosureJsonInputParams.COMMENTS.getValue()).value(this.comments).ignoreIfNull()
                .notExceedingLengthOf(500);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    public void validateForUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("GLClosure");
        baseDataValidator.reset().parameter(GLClosureJsonInputParams.COMMENTS.getValue()).value(this.comments).ignoreIfNull()
                .notExceedingLengthOf(500);
        baseDataValidator.reset().anyOfNotNull(this.comments);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
