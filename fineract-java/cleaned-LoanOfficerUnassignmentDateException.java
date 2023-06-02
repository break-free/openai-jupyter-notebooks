
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanOfficerUnassignmentDateException extends AbstractPlatformDomainRuleException {
    public LoanOfficerUnassignmentDateException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.loan.loanofficer.unassign.date." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
