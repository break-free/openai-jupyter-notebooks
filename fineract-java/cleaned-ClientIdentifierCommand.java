
package org.apache.fineract.portfolio.client.command;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class ClientIdentifierCommand {
    private final Long documentTypeId;
    private final String documentKey;
    private final String description;
    private final String status;
    public ClientIdentifierCommand(final Long documentTypeId, final String documentKey, final String statusString,
            final String description) {
        this.documentTypeId = documentTypeId;
        this.documentKey = documentKey;
        this.status = statusString;
        this.description = description;
    }
    public Long getDocumentTypeId() {
        return this.documentTypeId;
    }
    public String getDocumentKey() {
        return this.documentKey;
    }
    public String getDescription() {
        return this.description;
    }
    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("clientIdentifier");
        baseDataValidator.reset().parameter("documentTypeId").value(this.documentTypeId).notNull().integerGreaterThanZero();
        baseDataValidator.reset().parameter("documentKey").value(this.documentKey).notBlank();
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    public void validateForUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("clientIdentifier");
        baseDataValidator.reset().parameter("documentKey").value(this.documentKey).ignoreIfNull().notBlank();
        baseDataValidator.reset().anyOfNotNull(this.documentTypeId, this.documentKey);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
