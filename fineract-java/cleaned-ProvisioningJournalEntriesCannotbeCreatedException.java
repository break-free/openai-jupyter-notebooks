
package org.apache.fineract.accounting.provisioning.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProvisioningJournalEntriesCannotbeCreatedException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningJournalEntriesCannotbeCreatedException(LocalDate existingEntriesDate, LocalDate requestedDate) {
        super("error.msg.provisioning.journalentries.cannot.be.created", "Provisioning Journal Entries already created on later date "
                + existingEntriesDate + " than requested date " + requestedDate);
    }
}
