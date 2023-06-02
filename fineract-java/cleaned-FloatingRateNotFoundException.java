
package org.apache.fineract.portfolio.floatingrates.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class FloatingRateNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FloatingRateNotFoundException(final Long id) {
        super("error.msg.floatingrate.id.invalid", "Floating Rate with identifier " + id + " does not exist", id);
    }
    public FloatingRateNotFoundException(final String globalisationMessageCode) {
        super(globalisationMessageCode, "Floating Rate does not exist");
    }
    public FloatingRateNotFoundException(String globalisationMessageCode, EmptyResultDataAccessException e) {
        super(globalisationMessageCode, "Floating Rate does not exist", e);
    }
    public FloatingRateNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.floatingrate.id.invalid", "Floating Rate with identifier " + id + " does not exist", id, e);
    }
}
