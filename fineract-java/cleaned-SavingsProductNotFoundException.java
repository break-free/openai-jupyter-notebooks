
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class SavingsProductNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SavingsProductNotFoundException(final Long id) {
        super("error.msg.savingproduct.id.invalid", "Saving product with identifier " + id + " does not exist", id);
    }
    public SavingsProductNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.savingproduct.id.invalid", "Saving product with identifier " + id + " does not exist", id, e);
    }
}
