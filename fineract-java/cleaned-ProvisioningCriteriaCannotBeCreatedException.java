
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ProvisioningCriteriaCannotBeCreatedException extends AbstractPlatformDomainRuleException {
    public ProvisioningCriteriaCannotBeCreatedException(String globalisationMessageCode, String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
