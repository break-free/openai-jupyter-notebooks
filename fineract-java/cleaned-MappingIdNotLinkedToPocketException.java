
package org.apache.fineract.portfolio.self.pockets.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.portfolio.self.pockets.api.PocketApiConstants;
@SuppressWarnings("serial")
public class MappingIdNotLinkedToPocketException extends AbstractPlatformDomainRuleException {
    public MappingIdNotLinkedToPocketException(final Long id) {
        super(PocketApiConstants.mappingIdNotLinkedToPocketException, PocketApiConstants.mappingIdNotLinkedToPocketErrorMessage, id);
    }
}
