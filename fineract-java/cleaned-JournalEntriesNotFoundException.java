
package org.apache.fineract.accounting.journalentry.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class JournalEntriesNotFoundException extends AbstractPlatformResourceNotFoundException {
    public JournalEntriesNotFoundException(final String transactionId) {
        super("error.msg.journalEntries.transactionId.invalid",
                "Journal Entries with transaction Identifier " + transactionId + " does not exist or are not system generated/reversible ",
                transactionId);
    }
    public JournalEntriesNotFoundException(final Long entryId) {
        super("error.msg.journalEntries.id.invalid", "Journal Entry with entry Id " + entryId + " does not exist ", entryId);
    }
    public JournalEntriesNotFoundException(final Long entryId, EmptyResultDataAccessException e) {
        super("error.msg.journalEntries.id.invalid", "Journal Entry with entry Id " + entryId + " does not exist ", entryId, e);
    }
}
