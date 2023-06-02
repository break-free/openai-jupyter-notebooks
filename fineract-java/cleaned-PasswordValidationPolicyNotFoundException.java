
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class PasswordValidationPolicyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public PasswordValidationPolicyNotFoundException(final Long id) {
        super("error.msg.password.validation.policy.id.invalid", "Password Validation Policy with identifier " + id + " does not exist",
                id);
    }
    public PasswordValidationPolicyNotFoundException() {
        super("error.msg.password.validation.policy.not.found", "An active password validation policy was not found");
    }
    public PasswordValidationPolicyNotFoundException(EmptyResultDataAccessException e) {
        super("error.msg.password.validation.policy.not.found", "An active password validation policy was not found", e);
    }
}
