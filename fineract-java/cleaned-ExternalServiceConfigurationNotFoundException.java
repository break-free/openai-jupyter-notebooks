
package org.apache.fineract.infrastructure.configuration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ExternalServiceConfigurationNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ExternalServiceConfigurationNotFoundException(final String serviceName) {
        super("error.msg.externalservice.servicename.invalid", "Service Name`" + serviceName + "` does not exist", serviceName);
    }
    public ExternalServiceConfigurationNotFoundException(final String externalServiceName, final String name) {
        super("error.msg.externalservice.property.invalid",
                "Parameter`" + name + "` does not exist for the ServiceName `" + externalServiceName + "`", name, externalServiceName);
    }
}
