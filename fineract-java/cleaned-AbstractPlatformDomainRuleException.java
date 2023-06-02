
package org.apache.fineract.infrastructure.core.exception;
public abstract class AbstractPlatformDomainRuleException extends AbstractPlatformException {
    protected AbstractPlatformDomainRuleException(String globalisationMessageCode, String defaultUserMessage,
            Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
