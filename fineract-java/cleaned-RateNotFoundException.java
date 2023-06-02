
package org.apache.fineract.portfolio.rate.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class RateNotFoundException extends AbstractPlatformResourceNotFoundException {
    public RateNotFoundException(final Long id) {
        super("error.msg.rate.id.invalid", "Rate with identifier " + id + " does not exist", id);
    }
    public RateNotFoundException(final String name) {
        super("error.msg.rate.id.invalid", "Rate with name " + name + " does not exist", name);
    }
    public RateNotFoundException(String name, EmptyResultDataAccessException e) {
        super("error.msg.rate.id.invalid", "Rate with name " + name + " does not exist", name, e);
    }
    public RateNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.rate.id.invalid", "Rate with identifier " + id + " does not exist", id, e);
    }
}
