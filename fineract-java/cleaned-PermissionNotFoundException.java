
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class PermissionNotFoundException extends AbstractPlatformResourceNotFoundException {
    public PermissionNotFoundException(final String code) {
        super("error.msg.permission.code.invalid", "Permission with Code " + code + " does not exist", code);
    }
}
