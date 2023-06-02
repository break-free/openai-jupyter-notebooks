
package org.apache.fineract.interoperation.serialization;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.interoperation.data.InteropIdentifierRequestData;
import org.apache.fineract.interoperation.data.InteropQuoteRequestData;
import org.apache.fineract.interoperation.data.InteropTransactionRequestData;
import org.apache.fineract.interoperation.data.InteropTransferRequestData;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class InteropDataValidator {
    private final FromJsonHelper jsonHelper;
    @Autowired
    public InteropDataValidator(final FromJsonHelper fromJsonHelper) {
        this.jsonHelper = fromJsonHelper;
    }
    public InteropTransferRequestData validateAndParseTransferRequest(JsonCommand command) {
        final DataValidatorBuilder dataValidator = new DataValidatorBuilder(new ArrayList<>()).resource("interoperation.transfer");
        JsonObject element = extractJsonObject(command);
        InteropTransferRequestData result = InteropTransferRequestData.validateAndParse(dataValidator, element, jsonHelper);
        throwExceptionIfValidationWarningsExist(dataValidator);
        return result;
    }
    public InteropIdentifierRequestData validateAndParseCreateIdentifier(@NotNull InteropIdentifierType idType, @NotNull String idValue,
            String subIdOrType, JsonCommand command) {
        final DataValidatorBuilder dataValidator = new DataValidatorBuilder(new ArrayList<>()).resource("interoperation.identifier");
        JsonObject element = extractJsonObject(command);
        InteropIdentifierRequestData result = InteropIdentifierRequestData.validateAndParse(dataValidator, idType, idValue, subIdOrType,
                element, jsonHelper);
        throwExceptionIfValidationWarningsExist(dataValidator);
        return result;
    }
    public InteropTransactionRequestData validateAndParseCreateRequest(JsonCommand command) {
        final DataValidatorBuilder dataValidator = new DataValidatorBuilder(new ArrayList<>()).resource("interoperation.request");
        JsonObject element = extractJsonObject(command);
        InteropTransactionRequestData result = InteropTransactionRequestData.validateAndParse(dataValidator, element, jsonHelper);
        throwExceptionIfValidationWarningsExist(dataValidator);
        return result;
    }
    public InteropQuoteRequestData validateAndParseCreateQuote(JsonCommand command) {
        final DataValidatorBuilder dataValidator = new DataValidatorBuilder(new ArrayList<>()).resource("interoperation.quote");
        JsonObject element = extractJsonObject(command);
        InteropQuoteRequestData result = InteropQuoteRequestData.validateAndParse(dataValidator, element, jsonHelper);
        throwExceptionIfValidationWarningsExist(dataValidator);
        return result;
    }
    public InteropTransferRequestData validateAndParsePrepareTransfer(JsonCommand command) {
        return validateAndParseCreateTransfer(command);
    }
    public InteropTransferRequestData validateAndParseCreateTransfer(JsonCommand command) {
        final DataValidatorBuilder dataValidator = new DataValidatorBuilder(new ArrayList<>()).resource("interoperation.transfer");
        JsonObject element = extractJsonObject(command);
        InteropTransferRequestData result = InteropTransferRequestData.validateAndParse(dataValidator, element, jsonHelper);
        throwExceptionIfValidationWarningsExist(dataValidator);
        return result;
    }
    private JsonObject extractJsonObject(JsonCommand command) {
        String json = command.json();
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final JsonElement element = jsonHelper.parse(json);
        return element.getAsJsonObject();
    }
    private void throwExceptionIfValidationWarningsExist(DataValidatorBuilder dataValidator) {
        if (dataValidator.hasError()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidator.getDataValidationErrors());
        }
    }
}
