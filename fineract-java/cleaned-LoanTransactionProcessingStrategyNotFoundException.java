
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class LoanTransactionProcessingStrategyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanTransactionProcessingStrategyNotFoundException(final Long id) {
        super("error.msg.transactions.processing.strategy.id.invalid",
                "Loan transaction processing strategy with identifier " + id + " does not exist", id);
    }
}
