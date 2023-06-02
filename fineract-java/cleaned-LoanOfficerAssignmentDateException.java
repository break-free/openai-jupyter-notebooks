
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanOfficerAssignmentDateException extends AbstractPlatformDomainRuleException {
    public LoanOfficerAssignmentDateException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.loan.assignment.date." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
