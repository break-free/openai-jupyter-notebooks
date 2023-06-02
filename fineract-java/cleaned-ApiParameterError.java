
package org.apache.fineract.infrastructure.core.data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
public final class ApiParameterError {
    private final String developerMessage;
    private final String defaultUserMessage;
    private final String userMessageGlobalisationCode;
    private String parameterName;
    private final Object value;
    private final List<ApiErrorMessageArg> args;
    public static ApiParameterError generalError(final String globalisationMessageCode, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        return new ApiParameterError(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs, "id", null);
    }
    public static ApiParameterError resourceIdentifierNotFound(final String globalisationMessageCode, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        return new ApiParameterError(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs, "id", null);
    }
    public static ApiParameterError parameterError(final String globalisationMessageCode, final String defaultUserMessage,
            final String parameterName, final Object... defaultUserMessageArgs) {
        final ApiParameterError error = new ApiParameterError(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs,
                parameterName, null);
        return error;
    }
    public static ApiParameterError parameterErrorWithValue(final String globalisationMessageCode, final String defaultUserMessage,
            final String parameterName, final String value, final Object... defaultUserMessageArgs) {
        final ApiParameterError error = new ApiParameterError(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs,
                parameterName, value);
        return error;
    }
    private ApiParameterError(final String globalisationMessageCode, final String defaultUserMessage, final Object[] defaultUserMessageArgs,
            String parameterName, String value) {
        this.userMessageGlobalisationCode = globalisationMessageCode;
        this.developerMessage = defaultUserMessage;
        this.defaultUserMessage = defaultUserMessage;
        this.parameterName = parameterName;
        this.value = value;
        final List<ApiErrorMessageArg> messageArgs = new ArrayList<>();
        if (defaultUserMessageArgs != null) {
            for (final Object object : defaultUserMessageArgs) {
                if (object instanceof LocalDate) {
                    final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
                    final String formattedDate = dateFormatter.format((LocalDate) object);
                    messageArgs.add(ApiErrorMessageArg.from(formattedDate));
                } else {
                    messageArgs.add(ApiErrorMessageArg.from(object));
                }
            }
        }
        this.args = messageArgs;
    }
    public String getDeveloperMessage() {
        return this.developerMessage;
    }
    public String getDefaultUserMessage() {
        return this.defaultUserMessage;
    }
    public String getUserMessageGlobalisationCode() {
        return this.userMessageGlobalisationCode;
    }
    public String getParameterName() {
        return this.parameterName;
    }
    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }
    public Object getValue() {
        return this.value;
    }
    public List<ApiErrorMessageArg> getArgs() {
        return this.args;
    }
    @Override
    public String toString() {
        return developerMessage;
    }
}
