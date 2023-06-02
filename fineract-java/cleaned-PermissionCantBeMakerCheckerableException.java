
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class PermissionCantBeMakerCheckerableException extends AbstractPlatformResourceNotFoundException {
    public PermissionCantBeMakerCheckerableException(final String code) {
        super("error.msg.permission.code.not.makercheckerable", "Permission with Code " + code + " can't be maker-checkerable", code);
    }
}
