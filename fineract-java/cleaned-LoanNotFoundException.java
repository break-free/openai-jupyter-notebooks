
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class LoanNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanNotFoundException(final Long id) {
        super("error.msg.loan.id.invalid", "Loan with identifier " + id + " does not exist", id);
    }
    public LoanNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.loan.id.invalid", "Loan with identifier " + id + " does not exist", id, e);
    }
    public LoanNotFoundException(String accountId) {
        super("error.msg.loan.account.id.invalid", "Loan with account ID " + accountId + " does not exist", accountId);
    }
}
