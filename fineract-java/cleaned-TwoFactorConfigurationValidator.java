
package org.apache.fineract.infrastructure.security.data;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.security.constants.TwoFactorConfigurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
@Component
@ConditionalOnProperty("fineract.security.2fa.enabled")
public class TwoFactorConfigurationValidator {
    private final FromJsonHelper fromJsonHelper;
    @Autowired
    public TwoFactorConfigurationValidator(FromJsonHelper fromJsonHelper) {
        this.fromJsonHelper = fromJsonHelper;
    }
    public void validateForUpdate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        boolean atLeastOneParameterPassedForUpdate = false;
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromJsonHelper.checkForUnsupportedParameters(typeOfMap, json, TwoFactorConfigurationConstants.REQUEST_DATA_PARAMETERS);
        final JsonElement element = this.fromJsonHelper.parse(json);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(TwoFactorConfigurationConstants.RESOURCE_NAME);
        for (String parameterName : TwoFactorConfigurationConstants.BOOLEAN_PARAMETERS) {
            if (this.fromJsonHelper.parameterExists(parameterName, element)) {
                atLeastOneParameterPassedForUpdate = true;
                validateBooleanParameter(parameterName, element, baseDataValidator);
            }
        }
        for (String parameterName : TwoFactorConfigurationConstants.STRING_PARAMETERS) {
            if (this.fromJsonHelper.parameterExists(parameterName, element)) {
                atLeastOneParameterPassedForUpdate = true;
                validateStringParameter(parameterName, element, baseDataValidator);
            }
        }
        for (String parameterName : TwoFactorConfigurationConstants.NUMBER_PARAMETERS) {
            if (this.fromJsonHelper.parameterExists(parameterName, element)) {
                atLeastOneParameterPassedForUpdate = true;
                validateNumberParameter(parameterName, element, baseDataValidator);
            }
        }
        if (!atLeastOneParameterPassedForUpdate) {
            final Object forceError = null;
            baseDataValidator.reset().anyOfNotNull(forceError);
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
    private void validateBooleanParameter(final String name, final JsonElement element, final DataValidatorBuilder baseDataValidator) {
        final String value = this.fromJsonHelper.extractStringNamed(name, element);
        baseDataValidator.reset().parameter(name).value(value).notNull().trueOrFalseRequired(value);
    }
    private void validateStringParameter(final String name, final JsonElement element, final DataValidatorBuilder baseDataValidator) {
        final String value = this.fromJsonHelper.extractStringNamed(name, element);
        baseDataValidator.reset().parameter(name).value(value).notBlank().notExceedingLengthOf(1000);
    }
    private void validateNumberParameter(final String name, final JsonElement element, final DataValidatorBuilder baseDataValidator) {
        final Integer value = this.fromJsonHelper.extractIntegerSansLocaleNamed(name, element);
        baseDataValidator.reset().parameter(name).value(value).notNull().integerGreaterThanZero();
    }
}
