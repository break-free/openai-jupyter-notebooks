
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class GLAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GLAccountNotFoundException(final Long id) {
        super("error.msg.glaccount.id.invalid", "General Ledger account with identifier " + id + " does not exist ", id);
    }
    public GLAccountNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super("error.msg.glaccount.id.invalid", "General Ledger account with identifier " + id + " does not exist ", id, e);
    }
    public GLAccountNotFoundException(final String glCode) {
        super("error.msg.glaccount.code.invalid", "General Ledger account with GlCode " + glCode + " does not exist ", glCode);
    }
}
