
package org.apache.fineract.infrastructure.creditbureau.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CreditBureauCommandFromApiJsonDeserializer {
    private final Set<String> supportedParameters = new HashSet<>(Arrays.asList("alias", "isActive", "creditBureauId"));
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public CreditBureauCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateForCreate(final String json, final Long creditBureauId) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("CreditBureau");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        baseDataValidator.reset().value(creditBureauId).notBlank().integerGreaterThanZero();
        final String alias = this.fromApiJsonHelper.extractStringNamed("alias", element);
        baseDataValidator.reset().parameter("alias").value(alias).notNull().notBlank().notExceedingLengthOf(100);
        final String is_activeParameter = "isActive";
        if (this.fromApiJsonHelper.parameterExists(is_activeParameter, element)) {
            final boolean isActive = this.fromApiJsonHelper.extractBooleanNamed("isActive", element);
            baseDataValidator.reset().parameter("isActive").value(isActive).notNull().notBlank().trueOrFalseRequired(isActive);
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
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("CreditBureau");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final String creditBureauIdParameter = "creditBureauId";
        if (this.fromApiJsonHelper.parameterExists(creditBureauIdParameter, element)) {
            final Long creditBureauId = this.fromApiJsonHelper.extractLongNamed("creditBureauId", element);
            baseDataValidator.reset().parameter("creditBureauId").value(creditBureauId).notNull().notBlank().longGreaterThanZero();
        }
        final String is_activeParameter = "isActive";
        if (this.fromApiJsonHelper.parameterExists(is_activeParameter, element)) {
            final boolean isActive = this.fromApiJsonHelper.extractBooleanNamed("isActive", element);
            baseDataValidator.reset().parameter("isActive").value(isActive).notNull().notBlank().trueOrFalseRequired(isActive);
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
