
package org.apache.fineract.interoperation.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InteropTransferMissingException extends AbstractPlatformResourceNotFoundException {
    public InteropTransferMissingException(String accountId, String transferCode) {
        super("error.msg.interop.onhold.transfer.missing",
                "No matching transaction with transferCode " + transferCode + " found for account " + accountId);
    }
}
