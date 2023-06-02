
package org.apache.fineract.portfolio.products.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.beans.BeansException;
public class ResourceNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ResourceNotFoundException(String globalisationMessageCode, String defaultUserMessage, Object[] defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
    public ResourceNotFoundException() {
        super("", "", "");
    }
    public ResourceNotFoundException(BeansException e) {
        super("", "", "", e);
    }
}
