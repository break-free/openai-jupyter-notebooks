
package org.apache.fineract.infrastructure.security.exception;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class ResetPasswordException extends PlatformApiDataValidationException {
    public ResetPasswordException(final Long userId) {
        super("error.msg.password.outdated", "The password of the user with id " + userId + " has expired, please reset it",
                new ArrayList<ApiParameterError>(List.of(ApiParameterError.parameterError("error.msg.password.outdated",
                        "The password of the user with id " + userId + " has expired, please reset it", "userId", userId)))
        );
    }
}
