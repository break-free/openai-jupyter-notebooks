
package org.apache.fineract.infrastructure.core.exception;
public class PlatformDataIntegrityException extends AbstractPlatformException {
    private final String parameterName;
    public PlatformDataIntegrityException(final String globalisationMessageCode, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
        this.parameterName = null;
    }
    public PlatformDataIntegrityException(final String globalisationMessageCode, final String defaultUserMessage,
            final String parameterName, final Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
        this.parameterName = parameterName;
    }
    public String getParameterName() {
        return this.parameterName;
    }
}
