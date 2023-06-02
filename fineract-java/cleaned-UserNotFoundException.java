
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class UserNotFoundException extends AbstractPlatformResourceNotFoundException {
    public UserNotFoundException(final Long id) {
        super("error.msg.user.id.invalid", "User with ID " + id + " does not exist", id);
    }
    public UserNotFoundException(final String userName) {
        super("error.msg.user.name.not.found", "User with name '" + userName + "' does not exist");
    }
}
