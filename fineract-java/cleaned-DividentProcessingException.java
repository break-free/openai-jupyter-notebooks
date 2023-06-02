
package org.apache.fineract.portfolio.shareproducts.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DividentProcessingException extends AbstractPlatformDomainRuleException {
    public DividentProcessingException(final String msgcode, final String defaultUserMessage) {
        super("error.msg.divident.processing." + msgcode, defaultUserMessage);
    }
}
