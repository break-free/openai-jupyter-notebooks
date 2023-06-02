
package org.apache.fineract.infrastructure.security.exception;
public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException(final String message) {
        super(message);
    }
}
