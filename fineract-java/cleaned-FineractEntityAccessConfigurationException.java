
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityAccessType;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityType;
public class FineractEntityAccessConfigurationException extends AbstractPlatformDomainRuleException {
    public FineractEntityAccessConfigurationException(final Long firstEntityId, final FineractEntityType entityType1,
            final FineractEntityAccessType accessType, final FineractEntityType entityType2) {
        super("error.msg.entityaccess.config", "Error while getting entity access configuration for " + entityType1.getType() + ":"
                + firstEntityId + " with type " + accessType.toStr() + " against " + entityType2.getType());
    }
}
