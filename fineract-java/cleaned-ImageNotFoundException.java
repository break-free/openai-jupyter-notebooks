
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ImageNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ImageNotFoundException(final String resource, final Long resourceId) {
        super("error.msg.entity.image.invalid", "Image for resource " + resource + " with Identifier " + resourceId + " does not exist",
                resource, resourceId);
    }
    public ImageNotFoundException(String resource, Long resourceId, EmptyResultDataAccessException e) {
        super("error.msg.entity.image.invalid", "Image for resource " + resource + " with Identifier " + resourceId + " does not exist",
                resource, resourceId, e);
    }
}
