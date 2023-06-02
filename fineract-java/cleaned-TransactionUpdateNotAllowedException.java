
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformServiceUnavailableException;
public class TransactionUpdateNotAllowedException extends AbstractPlatformServiceUnavailableException {
    public TransactionUpdateNotAllowedException(final Long savingsId, final Long transactionId) {
        super("error.msg.saving.account.trasaction.update.notallowed",
                "Savings Account transaction update not allowed with savings identifier " + savingsId + " and trasaction identifier "
                        + transactionId,
                savingsId, transactionId);
    }
}
