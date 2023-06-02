
package org.apache.fineract.portfolio.client.data;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ClientChargeDataValidator {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public ClientChargeDataValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateAdd(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_CHARGES_ADD_REQUEST_DATA_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiConstants.CLIENT_CHARGES_RESOURCE_NAME);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final BigDecimal amount = this.fromApiJsonHelper.extractBigDecimalWithLocaleNamed(ClientApiConstants.amountParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.amountParamName).value(amount).notNull().positiveAmount();
        final LocalDate dueDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dueAsOfDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.dueAsOfDateParamName).value(dueDate).notNull();
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    public void validateUpdate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_CHARGES_ADD_REQUEST_DATA_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiConstants.CLIENT_CHARGES_RESOURCE_NAME);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.amountParamName, element)) {
            final BigDecimal amount = this.fromApiJsonHelper.extractBigDecimalWithLocaleNamed(ClientApiConstants.amountParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.amountParamName).value(amount).notNull().positiveAmount();
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.dueAsOfDateParamName, element)) {
            final LocalDate dueDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dueAsOfDateParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.dueAsOfDateParamName).value(dueDate).notNull();
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    public void validatePayCharge(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_CHARGES_PAY_CHARGE_REQUEST_DATA_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiConstants.CLIENT_CHARGES_RESOURCE_NAME);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final BigDecimal amount = this.fromApiJsonHelper.extractBigDecimalWithLocaleNamed(ClientApiConstants.amountParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.amountParamName).value(amount).notNull().positiveAmount();
        final LocalDate transactionDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.transactionDateParamName,
                element);
        baseDataValidator.reset().parameter(ClientApiConstants.transactionDateParamName).value(transactionDate).notNull();
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}
