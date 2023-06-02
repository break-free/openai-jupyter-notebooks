
package org.apache.fineract.accounting.provisioning.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.accounting.provisioning.constant.ProvisioningEntriesApiConstants;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.organisation.provisioning.exception.ProvisioningCriteriaCannotBeCreatedException;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ProvisioningEntriesDefinitionJsonDeserializer implements ProvisioningEntriesApiConstants {
    private static final Set<String> supportedParameters = new HashSet<>(
            Arrays.asList(JSON_DATE_PARAM, JSON_DATEFORMAT_PARAM, JSON_LOCALE_PARAM, JSON_CREATEJOURNALENTRIES_PARAM));
    private final FromJsonHelper fromApiJsonHelper;
    public void validateForCreate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new ProvisioningCriteriaCannotBeCreatedException("error.msg.provisioningentry.cannot.be.created",
                    "locale, dateformat, date, createjournalentries params are missing in the request");
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("provisioningcriteria");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(element.getAsJsonObject());
        baseDataValidator.reset().parameter(JSON_DATEFORMAT_PARAM).value(locale).notNull();
        final String dateformat = this.fromApiJsonHelper.extractDateFormatParameter(element.getAsJsonObject());
        baseDataValidator.reset().parameter(JSON_DATEFORMAT_PARAM).value(dateformat).notBlank();
        LocalDate localDate = this.fromApiJsonHelper.extractLocalDateNamed(JSON_DATE_PARAM, element);
        baseDataValidator.reset().parameter(JSON_DATE_PARAM).value(localDate).notBlank();
        baseDataValidator.reset().parameter(JSON_DATE_PARAM).value(localDate).validateDateBeforeOrEqual(DateUtils.getBusinessLocalDate());
        if (this.fromApiJsonHelper.parameterExists(JSON_CREATEJOURNALENTRIES_PARAM, element)) {
            Boolean bool = this.fromApiJsonHelper.extractBooleanNamed(JSON_CREATEJOURNALENTRIES_PARAM, element);
            baseDataValidator.reset().parameter(JSON_CREATEJOURNALENTRIES_PARAM).value(bool).validateForBooleanValue();
        }
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}
