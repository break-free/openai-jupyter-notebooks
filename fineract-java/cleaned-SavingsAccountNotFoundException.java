
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class SavingsAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SavingsAccountNotFoundException(final Long id) {
        super("error.msg.saving.account.id.invalid", "Savings account with identifier " + id + " does not exist", id);
    }
    public SavingsAccountNotFoundException(final String externalId) {
        super("error.msg.saving.account.id.invalid", "Savings account with external identifier " + externalId + " does not exist",
                externalId);
    }
    public SavingsAccountNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.saving.account.id.invalid", "Savings account with identifier " + id + " does not exist", id, e);
    }
}
