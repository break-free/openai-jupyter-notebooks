
package org.apache.fineract.infrastructure.core.service;
public class PlatformEmailSendException extends RuntimeException {
    public PlatformEmailSendException(final Throwable e) {
        super(e);
    }
}
