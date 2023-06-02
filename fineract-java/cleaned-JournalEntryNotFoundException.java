
package org.apache.fineract.accounting.journalentry.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class JournalEntryNotFoundException extends AbstractPlatformResourceNotFoundException {
    public JournalEntryNotFoundException(final String transactionId) {
        super("error.msg.journalEntries.transactionId.invalid",
                "Journal Entries with transaction Identifier " + transactionId + " does not exist or are not system generated/reversible ",
                transactionId);
    }
    public JournalEntryNotFoundException(final Long entryId) {
        super("error.msg.journalEntries.id.invalid", "Journal Entry with entry Id " + entryId + " does not exist ", entryId);
    }
}
