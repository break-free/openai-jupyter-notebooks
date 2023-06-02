
package org.apache.fineract.infrastructure.codes.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.CodeConstants.CodevalueJSONinputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class CodeValueCommandFromApiJsonDeserializer {
    private final Set<String> supportedParameters = CodevalueJSONinputParams.getAllValues();
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public CodeValueCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateForCreate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("code.value");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final String name = this.fromApiJsonHelper.extractStringNamed(CodevalueJSONinputParams.NAME.getValue(), element);
        baseDataValidator.reset().parameter(CodevalueJSONinputParams.NAME.getValue()).value(name).notBlank().notExceedingLengthOf(100);
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.DESCRIPTION.getValue(), element)) {
            final String description = this.fromApiJsonHelper.extractStringNamed(CodevalueJSONinputParams.DESCRIPTION.getValue(), element);
            baseDataValidator.reset().parameter(CodevalueJSONinputParams.DESCRIPTION.getValue()).value(description)
                    .notExceedingLengthOf(500);
        }
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.POSITION.getValue(), element)) {
            this.fromApiJsonHelper.extractIntegerSansLocaleNamed(CodevalueJSONinputParams.POSITION.getValue(), element);
        }
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.IS_ACTIVE.getValue(), element)) {
            final Boolean isActive = this.fromApiJsonHelper.extractBooleanNamed(CodevalueJSONinputParams.IS_ACTIVE.getValue(), element);
            baseDataValidator.reset().parameter(CodevalueJSONinputParams.IS_ACTIVE.getValue()).value(isActive).validateForBooleanValue();
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    public void validateForUpdate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("code.value");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.NAME.getValue(), element)) {
            final String name = this.fromApiJsonHelper.extractStringNamed(CodevalueJSONinputParams.NAME.getValue(), element);
            baseDataValidator.reset().parameter(CodevalueJSONinputParams.NAME.getValue()).value(name).notBlank().notExceedingLengthOf(100);
        }
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.DESCRIPTION.getValue(), element)) {
            final String description = this.fromApiJsonHelper.extractStringNamed(CodevalueJSONinputParams.DESCRIPTION.getValue(), element);
            baseDataValidator.reset().parameter(CodevalueJSONinputParams.DESCRIPTION.getValue()).value(description)
                    .notExceedingLengthOf(500);
        }
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.POSITION.getValue(), element)) {
            this.fromApiJsonHelper.extractIntegerSansLocaleNamed(CodevalueJSONinputParams.POSITION.getValue(), element);
        }
        if (this.fromApiJsonHelper.parameterExists(CodevalueJSONinputParams.IS_ACTIVE.getValue(), element)) {
            final Boolean isActive = this.fromApiJsonHelper.extractBooleanNamed(CodevalueJSONinputParams.IS_ACTIVE.getValue(), element);
            baseDataValidator.reset().parameter(CodevalueJSONinputParams.IS_ACTIVE.getValue()).value(isActive).validateForBooleanValue();
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
