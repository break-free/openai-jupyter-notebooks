
package org.apache.fineract.accounting.closure.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class GLClosureNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GLClosureNotFoundException(final Long id) {
        super("error.msg.glclosure.id.invalid", "Accounting Closure with identifier " + id + " does not exist", id);
    }
    public GLClosureNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super("error.msg.glclosure.id.invalid", "Accounting Closure with identifier " + id + " does not exist", id, e);
    }
}
