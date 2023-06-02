
package org.apache.fineract.infrastructure.campaigns.email.data;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.campaigns.email.ScheduledEmailConstants;
import org.apache.fineract.infrastructure.campaigns.email.domain.ScheduledEmailAttachmentFileFormat;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class EmailDataValidator {
    private final FromJsonHelper fromApiJsonHelper;
    private static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    @Autowired
    public EmailDataValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateCreateRequest(final JsonCommand jsonCommand) {
        final String jsonString = jsonCommand.json();
        final JsonElement jsonElement = jsonCommand.parsedJson();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
        final Type typeToken = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeToken, jsonString, ScheduledEmailConstants.CREATE_REQUEST_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder dataValidatorBuilder = new DataValidatorBuilder(dataValidationErrors)
                .resource(StringUtils.lowerCase(ScheduledEmailConstants.SCHEDULED_EMAIL_ENTITY_NAME));
        final String name = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.NAME_PARAM_NAME, jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.NAME_PARAM_NAME).value(name).notBlank().notExceedingLengthOf(100);
        final String startDateTime = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.START_DATE_TIME_PARAM_NAME,
                jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.START_DATE_TIME_PARAM_NAME).value(startDateTime).notBlank();
        final Integer stretchyReportId = this.fromApiJsonHelper
                .extractIntegerWithLocaleNamed(ScheduledEmailConstants.STRETCHY_REPORT_ID_PARAM_NAME, jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.STRETCHY_REPORT_ID_PARAM_NAME).value(stretchyReportId).notNull()
                .integerGreaterThanZero();
        final String emailRecipients = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_RECIPIENTS_PARAM_NAME,
                jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_RECIPIENTS_PARAM_NAME).value(emailRecipients).notBlank();
        final String emailSubject = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_SUBJECT_PARAM_NAME,
                jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_SUBJECT_PARAM_NAME).value(emailSubject).notBlank()
                .notExceedingLengthOf(100);
        final String emailMessage = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_MESSAGE_PARAM_NAME,
                jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_MESSAGE_PARAM_NAME).value(emailMessage).notBlank();
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME, jsonElement)) {
            final Boolean isActive = this.fromApiJsonHelper.extractBooleanNamed(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME, jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME).value(isActive).notNull();
        }
        final Integer emailAttachmentFileFormatId = this.fromApiJsonHelper
                .extractIntegerSansLocaleNamed(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME, jsonElement);
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME)
                .value(emailAttachmentFileFormatId).notNull();
        if (emailAttachmentFileFormatId != null) {
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME)
                    .value(emailAttachmentFileFormatId).isOneOfTheseValues(ScheduledEmailAttachmentFileFormat.validValues());
        }
        final String dateFormat = jsonCommand.dateFormat();
        dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.DATE_FORMAT_PARAM_NAME).value(dateFormat).notBlank();
        if (StringUtils.isNotEmpty(dateFormat)) {
            try {
                final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat).withLocale(jsonCommand.extractLocale());
                LocalDateTime.parse(startDateTime, dateTimeFormatter);
            }
            catch (IllegalArgumentException ex) {
                dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.DATE_FORMAT_PARAM_NAME).value(dateFormat)
                        .failWithCode("invalid.date.format");
            }
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    public void validateUpdateRequest(final JsonCommand jsonCommand) {
        final String jsonString = jsonCommand.json();
        final JsonElement jsonElement = jsonCommand.parsedJson();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
        final Type typeToken = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeToken, jsonString, ScheduledEmailConstants.UPDATE_REQUEST_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder dataValidatorBuilder = new DataValidatorBuilder(dataValidationErrors)
                .resource(StringUtils.lowerCase(ScheduledEmailConstants.SCHEDULED_EMAIL_ENTITY_NAME));
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.NAME_PARAM_NAME, jsonElement)) {
            final String name = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.NAME_PARAM_NAME, jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.NAME_PARAM_NAME).value(name).notBlank()
                    .notExceedingLengthOf(100);
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.STRETCHY_REPORT_ID_PARAM_NAME, jsonElement)) {
            final Integer stretchyReportId = this.fromApiJsonHelper
                    .extractIntegerWithLocaleNamed(ScheduledEmailConstants.STRETCHY_REPORT_ID_PARAM_NAME, jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.STRETCHY_REPORT_ID_PARAM_NAME).value(stretchyReportId).notNull()
                    .integerGreaterThanZero();
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.EMAIL_RECIPIENTS_PARAM_NAME, jsonElement)) {
            final String emailRecipients = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_RECIPIENTS_PARAM_NAME,
                    jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_RECIPIENTS_PARAM_NAME).value(emailRecipients).notBlank();
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.EMAIL_SUBJECT_PARAM_NAME, jsonElement)) {
            final String emailSubject = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_SUBJECT_PARAM_NAME,
                    jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_SUBJECT_PARAM_NAME).value(emailSubject).notBlank()
                    .notExceedingLengthOf(100);
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.EMAIL_MESSAGE_PARAM_NAME, jsonElement)) {
            final String emailMessage = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.EMAIL_MESSAGE_PARAM_NAME,
                    jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_MESSAGE_PARAM_NAME).value(emailMessage).notBlank();
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME, jsonElement)) {
            final Boolean isActive = this.fromApiJsonHelper.extractBooleanNamed(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME, jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.IS_ACTIVE_PARAM_NAME).value(isActive).notNull();
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME, jsonElement)) {
            final Integer emailAttachmentFileFormatId = this.fromApiJsonHelper
                    .extractIntegerSansLocaleNamed(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME, jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME)
                    .value(emailAttachmentFileFormatId).notNull();
            if (emailAttachmentFileFormatId != null) {
                dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.EMAIL_ATTACHMENT_FILE_FORMAT_ID_PARAM_NAME)
                        .value(emailAttachmentFileFormatId).isOneOfTheseValues(ScheduledEmailAttachmentFileFormat.validValues());
            }
        }
        if (this.fromApiJsonHelper.parameterExists(ScheduledEmailConstants.START_DATE_TIME_PARAM_NAME, jsonElement)) {
            final String dateFormat = jsonCommand.dateFormat();
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.DATE_FORMAT_PARAM_NAME).value(dateFormat).notBlank();
            final String startDateTime = this.fromApiJsonHelper.extractStringNamed(ScheduledEmailConstants.START_DATE_TIME_PARAM_NAME,
                    jsonElement);
            dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.START_DATE_TIME_PARAM_NAME).value(startDateTime).notBlank();
            if (StringUtils.isNotEmpty(dateFormat)) {
                try {
                    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
                            .withLocale(jsonCommand.extractLocale());
                    LocalDateTime.parse(startDateTime, dateTimeFormatter);
                }
                catch (IllegalArgumentException ex) {
                    dataValidatorBuilder.reset().parameter(ScheduledEmailConstants.DATE_FORMAT_PARAM_NAME).value(dateFormat)
                            .failWithCode("invalid.date.format");
                }
            }
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        if (email.endsWith(".")) {
            return false;
        }
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            return false;
        }
        return true;
    }
    public Set<String> validateEmailRecipients(String emailRecipients) {
        Set<String> emailRecipientsSet = new HashSet<>();
        if (emailRecipients != null) {
            Iterable<String> split = Splitter.on(',').split(emailRecipients);
            for (String emailAddress : split) {
                emailAddress = emailAddress.trim();
                if (this.isValidEmail(emailAddress)) {
                    emailRecipientsSet.add(emailAddress);
                }
            }
        }
        return emailRecipientsSet;
    }
    public HashMap<String, String> validateStretchyReportParamMap(String stretchyReportParamMap) {
        HashMap<String, String> stretchyReportParamHashMap = new HashMap<>();
        if (!StringUtils.isEmpty(stretchyReportParamMap)) {
            try {
                stretchyReportParamHashMap = new ObjectMapper().readValue(stretchyReportParamMap,
                        new TypeReference<HashMap<String, String>>() {});
            }
            catch (Exception e) {
                stretchyReportParamHashMap = null;
            }
        }
        return stretchyReportParamHashMap;
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}
