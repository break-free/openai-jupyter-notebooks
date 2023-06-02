
package org.apache.fineract.accounting.financialactivityaccount.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.financialactivityaccount.api.FinancialActivityAccountsJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public final class FinancialActivityAccountDataValidator {
    private final Set<String> supportedParameters = FinancialActivityAccountsJsonInputParams.getAllValues();
    private final String paramNameForFinancialActivity = FinancialActivityAccountsJsonInputParams.FINANCIAL_ACTIVITY_ID.getValue();
    private final String paramNameForGLAccount = FinancialActivityAccountsJsonInputParams.GL_ACCOUNT_ID.getValue();
    private final FromJsonHelper fromApiJsonHelper;
    public void validateForCreate(final String json) {
        validateJSONAndCheckForUnsupportedParams(json);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = getDataValidator(dataValidationErrors);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final Integer financialActivityId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(paramNameForFinancialActivity, element);
        baseDataValidator.reset().parameter(paramNameForFinancialActivity).value(financialActivityId).notNull().isOneOfTheseValues(
                FinancialActivity.ASSET_TRANSFER.getValue(), FinancialActivity.LIABILITY_TRANSFER.getValue(),
                FinancialActivity.CASH_AT_MAINVAULT.getValue(), FinancialActivity.CASH_AT_TELLER.getValue(),
                FinancialActivity.OPENING_BALANCES_TRANSFER_CONTRA.getValue(), FinancialActivity.ASSET_FUND_SOURCE.getValue(),
                FinancialActivity.PAYABLE_DIVIDENDS.getValue());
        final Long glAccountId = this.fromApiJsonHelper.extractLongNamed(paramNameForGLAccount, element);
        baseDataValidator.reset().parameter(paramNameForGLAccount).value(glAccountId).notNull().integerGreaterThanZero();
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private DataValidatorBuilder getDataValidator(final List<ApiParameterError> dataValidationErrors) {
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("financialactivityaccount");
        return baseDataValidator;
    }
    public void validateForUpdate(final String json) {
        validateJSONAndCheckForUnsupportedParams(json);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = getDataValidator(dataValidationErrors);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        if (this.fromApiJsonHelper.parameterExists(paramNameForFinancialActivity, element)) {
            final Integer financialActivityId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(paramNameForFinancialActivity,
                    element);
            baseDataValidator.reset().parameter(paramNameForFinancialActivity).value(financialActivityId).ignoreIfNull().isOneOfTheseValues(
                    FinancialActivity.ASSET_TRANSFER.getValue(), FinancialActivity.LIABILITY_TRANSFER.getValue(),
                    FinancialActivity.OPENING_BALANCES_TRANSFER_CONTRA.getValue(), FinancialActivity.ASSET_FUND_SOURCE.getValue(),
                    FinancialActivity.PAYABLE_DIVIDENDS.getValue());
        }
        if (this.fromApiJsonHelper.parameterExists(paramNameForGLAccount, element)) {
            final Long glAccountId = this.fromApiJsonHelper.extractLongNamed(paramNameForGLAccount, element);
            baseDataValidator.reset().parameter(paramNameForGLAccount).value(glAccountId).ignoreIfNull().integerGreaterThanZero();
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void validateJSONAndCheckForUnsupportedParams(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
