
package org.apache.fineract.portfolio.accounts.exceptions;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ShareAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ShareAccountNotFoundException(final Long id) {
        super("error.msg.shareaccount.id.invalid", " Account with identifier " + id + " does not exist", id);
    }
    public ShareAccountNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.shareaccount.id.invalid", " Account with identifier " + id + " does not exist", id, e);
    }
}
