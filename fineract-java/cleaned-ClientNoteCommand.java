
package org.apache.fineract.portfolio.client.command;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class ClientNoteCommand {
    private final String note;
    public ClientNoteCommand(final String note) {
        this.note = note;
    }
    public void validate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("clientnote");
        baseDataValidator.reset().parameter("note").value(this.note).notBlank().notExceedingLengthOf(1000);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
