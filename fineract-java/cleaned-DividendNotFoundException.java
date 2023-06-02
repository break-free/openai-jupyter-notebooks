
package org.apache.fineract.portfolio.shareproducts.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class DividendNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DividendNotFoundException(final Long id, String type) {
        super("error.msg.dividend.id.invalid", type + " dividend with identifier " + id + " does not exist", id);
    }
}
