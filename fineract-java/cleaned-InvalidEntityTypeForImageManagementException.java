
package org.apache.fineract.infrastructure.documentmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InvalidEntityTypeForImageManagementException extends AbstractPlatformResourceNotFoundException {
    public InvalidEntityTypeForImageManagementException(String imageType) {
        super("error.imagemanagement.entitytype.invalid", "Image Management is not support for the Entity Type: " + imageType);
    }
}
