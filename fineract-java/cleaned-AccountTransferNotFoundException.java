
package org.apache.fineract.portfolio.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class AccountTransferNotFoundException extends AbstractPlatformResourceNotFoundException {
    public AccountTransferNotFoundException(final Long id) {
        super("error.msg.accounttransfer.id.invalid", "Account transfer with identifier " + id + " does not exist", id);
    }
    public AccountTransferNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.accounttransfer.id.invalid", "Account transfer with identifier " + id + " does not exist", id, e);
    }
}
