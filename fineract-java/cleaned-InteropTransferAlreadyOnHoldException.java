
package org.apache.fineract.interoperation.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InteropTransferAlreadyOnHoldException extends AbstractPlatformDomainRuleException {
    public InteropTransferAlreadyOnHoldException(String accountId, String transferCode) {
        super("error.msg.interop.transfer.already.committed",
                "Transaction with transferCode " + transferCode + " already committed for account " + accountId);
    }
}
