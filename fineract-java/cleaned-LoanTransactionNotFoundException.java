
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class LoanTransactionNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanTransactionNotFoundException(final Long id) {
        super("error.msg.loan.id.invalid", "Transaction with identifier " + id + " does not exist", id);
    }
    public LoanTransactionNotFoundException(final Long id, final Long loanId) {
        super("error.msg.loan.id.invalid", "Transaction with identifier " + id + " does not exist for loan with identifier " + loanId + ".",
                id, loanId);
    }
    public LoanTransactionNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.loan.id.invalid", "Transaction with identifier " + id + " does not exist", id, e);
    }
}
