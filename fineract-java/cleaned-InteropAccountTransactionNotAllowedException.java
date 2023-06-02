
package org.apache.fineract.interoperation.exception;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InteropAccountTransactionNotAllowedException extends AbstractPlatformDomainRuleException {
    public InteropAccountTransactionNotAllowedException(@NotNull String accountId) {
        super("error.msg.interop.transaction.not.allowed", "Transaction not allowed on account " + accountId);
    }
}
