
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsOfficerUnassignmentDateException extends AbstractPlatformDomainRuleException {
    public SavingsOfficerUnassignmentDateException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.savings.savingsofficer.unassign.date." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
