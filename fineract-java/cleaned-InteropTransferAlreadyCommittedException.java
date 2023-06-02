
package org.apache.fineract.interoperation.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InteropTransferAlreadyCommittedException extends AbstractPlatformDomainRuleException {
    public InteropTransferAlreadyCommittedException(String accountId, String transferCode) {
        super("error.msg.interop.transfer.already.on.hold",
                "Transaction with transferCode " + transferCode + " already on hold for account " + accountId);
    }
}
