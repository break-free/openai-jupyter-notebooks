
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ExceedingTrancheCountException extends AbstractPlatformDomainRuleException {
    public ExceedingTrancheCountException(final String entity, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + ".exceeding.max.tranche.count", defaultUserMessage, defaultUserMessageArgs);
    }
}
