
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsAccountClosingNotAllowedException extends AbstractPlatformDomainRuleException {
    public SavingsAccountClosingNotAllowedException(final String entity, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + ".saving.account.close.notallowed", defaultUserMessage, defaultUserMessageArgs);
    }
}
