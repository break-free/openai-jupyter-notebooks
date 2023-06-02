
package org.apache.fineract.infrastructure.configuration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GlobalConfigurationPropertyCannotBeModfied extends AbstractPlatformDomainRuleException {
    public GlobalConfigurationPropertyCannotBeModfied(final Long configId) {
        super("error.msg.configuration.id.not.modifiable", "Configuration identifier `" + configId + "` cannot be modified",
                new Object[] { configId });
    }
}
