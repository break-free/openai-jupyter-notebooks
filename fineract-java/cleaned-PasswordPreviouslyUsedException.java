
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class PasswordPreviouslyUsedException extends PlatformApiDataValidationException {
    public PasswordPreviouslyUsedException() {
        super("error.msg.password.already.used", "The submitted password has already been used in the past", null);
    }
}
