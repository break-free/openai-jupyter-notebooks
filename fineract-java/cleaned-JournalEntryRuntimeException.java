
package org.apache.fineract.accounting.journalentry.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class JournalEntryRuntimeException extends AbstractPlatformDomainRuleException {
    public JournalEntryRuntimeException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
