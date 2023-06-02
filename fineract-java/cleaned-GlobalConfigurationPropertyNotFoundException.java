
package org.apache.fineract.infrastructure.configuration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class GlobalConfigurationPropertyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GlobalConfigurationPropertyNotFoundException(final String propertyName) {
        super("error.msg.configuration.property.invalid", "Configuration property `" + propertyName + "` does not exist", propertyName);
    }
    public GlobalConfigurationPropertyNotFoundException(final Long configId) {
        super("error.msg.configuration.id.invalid", "Configuration identifier `" + configId + "` does not exist", configId);
    }
}
