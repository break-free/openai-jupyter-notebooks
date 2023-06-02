
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ProvisioningCategoryCannotBeDeletedException extends AbstractPlatformDomainRuleException {
    public ProvisioningCategoryCannotBeDeletedException(final String globalisationMessageCode, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
