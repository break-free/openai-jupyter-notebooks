
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class LoanProductNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanProductNotFoundException(final Long id) {
        super("error.msg.loanproduct.id.invalid", "Loan product with identifier " + id + " does not exist", id);
    }
    public LoanProductNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.loanproduct.id.invalid", "Loan product with identifier " + id + " does not exist", id, e);
    }
}
