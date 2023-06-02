
package org.apache.fineract.organisation.office.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidOfficeException extends AbstractPlatformDomainRuleException {
    public InvalidOfficeException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + "." + postFix + ".invalid.office", defaultUserMessage, defaultUserMessageArgs);
    }
}
