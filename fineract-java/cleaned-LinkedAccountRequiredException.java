
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LinkedAccountRequiredException extends AbstractPlatformDomainRuleException {
    public LinkedAccountRequiredException(final String entity, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + ".requires.linked.account", defaultUserMessage, defaultUserMessageArgs);
    }
}
