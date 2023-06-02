
package org.apache.fineract.portfolio.transfer.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientNotAwaitingTransferApprovalException extends AbstractPlatformDomainRuleException {
    public ClientNotAwaitingTransferApprovalException(final Long clientId) {
        super("error.msg.client.not.awaiting.transfer.approval.exception", "The Client with id `" + clientId + "` is not awaiting transfer",
                clientId);
    }
}
