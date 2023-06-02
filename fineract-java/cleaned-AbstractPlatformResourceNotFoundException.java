
package org.apache.fineract.infrastructure.core.exception;
public abstract class AbstractPlatformResourceNotFoundException extends AbstractPlatformException {
    protected AbstractPlatformResourceNotFoundException(final String globalisationMessageCode, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
