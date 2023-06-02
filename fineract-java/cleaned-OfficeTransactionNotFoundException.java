
package org.apache.fineract.organisation.office.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class OfficeTransactionNotFoundException extends AbstractPlatformResourceNotFoundException {
    public OfficeTransactionNotFoundException(final Long id) {
        super("error.msg.officetransaction.id.invalid", "Office transaction with identifier " + id + " does not exist", id);
    }
}
