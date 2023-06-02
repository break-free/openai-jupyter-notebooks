
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MultiDisbursementDataNotAllowedException extends AbstractPlatformDomainRuleException {
    public MultiDisbursementDataNotAllowedException(final String entity, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + ".not.allowed", defaultUserMessage, defaultUserMessageArgs);
    }
}
