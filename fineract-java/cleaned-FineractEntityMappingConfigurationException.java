
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class FineractEntityMappingConfigurationException extends AbstractPlatformDomainRuleException {
    public FineractEntityMappingConfigurationException() {
        super("error.msg.entityaccess.config", "Error while getting entity maping configuration ");
    }
}
