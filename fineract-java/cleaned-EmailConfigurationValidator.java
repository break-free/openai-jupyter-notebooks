
package org.apache.fineract.infrastructure.campaigns.email.data;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.campaigns.email.EmailApiConstants;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class EmailConfigurationValidator {
    private final FromJsonHelper fromApiJsonHelper;
    private static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Set<String> supportedParams = new HashSet<String>(Arrays.asList(EmailApiConstants.SMTP_PORT,
            EmailApiConstants.SMTP_PASSWORD, EmailApiConstants.SMTP_USERNAME, EmailApiConstants.SMTP_SERVER));
    @Autowired
    public EmailConfigurationValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateUpdateConfiguration(String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, EmailConfigurationValidator.supportedParams);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(EmailApiConstants.RESOURCE_NAME);
        final String smtpUsername = this.fromApiJsonHelper.extractStringNamed(EmailApiConstants.SMTP_USERNAME, element);
        baseDataValidator.reset().parameter(EmailApiConstants.SMTP_USERNAME).value(smtpUsername).notBlank().notExceedingLengthOf(150);
        final String smtpPassword = this.fromApiJsonHelper.extractStringNamed(EmailApiConstants.SMTP_PASSWORD, element);
        baseDataValidator.reset().parameter(EmailApiConstants.SMTP_PASSWORD).value(smtpPassword).notBlank().notExceedingLengthOf(50);
        final String smtpServer = this.fromApiJsonHelper.extractStringNamed(EmailApiConstants.SMTP_SERVER, element);
        baseDataValidator.reset().parameter(EmailApiConstants.SMTP_SERVER).value(smtpServer).notBlank().notExceedingLengthOf(100);
        final Long smtpPort = this.fromApiJsonHelper.extractLongNamed(EmailApiConstants.SMTP_PORT, element);
        baseDataValidator.reset().parameter(EmailApiConstants.SMTP_PORT).value(smtpPort).notNull().integerGreaterThanZero();
        this.throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else if (email.endsWith(".")) {
            return false;
        }
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            return false;
        }
        return true;
    }
}
