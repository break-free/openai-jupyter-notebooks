
package org.apache.fineract.portfolio.self.pockets.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.portfolio.self.pockets.api.PocketApiConstants;
@SuppressWarnings("serial")
public class PocketNotFoundException extends AbstractPlatformDomainRuleException {
    public PocketNotFoundException() {
        super(PocketApiConstants.pocketNotFoundException, PocketApiConstants.pocketNotFoundErrorMessage);
    }
}
