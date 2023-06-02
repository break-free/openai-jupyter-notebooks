
package org.apache.fineract.infrastructure.core.exception;
public final class GeneralPlatformDomainRuleException extends AbstractPlatformDomainRuleException {
    public GeneralPlatformDomainRuleException(String globalisationMessageCode, String defaultUserMessage,
            Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
