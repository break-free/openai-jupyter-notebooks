
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ChargeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ChargeNotFoundException(final Long id) {
        super("error.msg.charge.id.invalid", "Charge with identifier " + id + " does not exist", id);
    }
    public ChargeNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.charge.id.invalid", "Charge with identifier " + id + " does not exist", id, e);
    }
}
