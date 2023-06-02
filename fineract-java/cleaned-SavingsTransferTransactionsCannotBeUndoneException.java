
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsTransferTransactionsCannotBeUndoneException extends AbstractPlatformDomainRuleException {
    public SavingsTransferTransactionsCannotBeUndoneException(final Long transactionId) {
        super("error.msg.savings.transfer.transactions.cannot.be.undone",
                "Transactions related to savings account transfers cannot be undone", transactionId);
    }
}
