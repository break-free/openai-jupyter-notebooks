
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TellerNotFoundException extends AbstractPlatformResourceNotFoundException {
    private static final String ERROR_MESSAGE_CODE = "error.msg.teller.not.found";
    private static final String DEFAULT_ERROR_MESSAGE = "Teller with identifier {0,number,long} not found!";
    public TellerNotFoundException(Long tellerId) {
        super(ERROR_MESSAGE_CODE, DEFAULT_ERROR_MESSAGE, tellerId);
    }
}
