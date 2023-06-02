
package org.apache.fineract.accounting.provisioning.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProvisioningEntryAlreadyCreatedException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningEntryAlreadyCreatedException(Long id, LocalDate date) {
        super("error.msg.provisioningentry.already.exists.for.this.date",
                "ProvisioningEntry with identifier " + id + " exists for given date" + date, id);
    }
}
