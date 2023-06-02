
package org.apache.fineract.accounting.journalentry.service;
import java.time.LocalDate;
import org.apache.fineract.accounting.journalentry.data.JournalEntryAssociationParametersData;
import org.apache.fineract.accounting.journalentry.data.JournalEntryData;
import org.apache.fineract.accounting.journalentry.data.OfficeOpeningBalancesData;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
public interface JournalEntryReadPlatformService {
    JournalEntryData retrieveGLJournalEntryById(long glJournalEntryId, JournalEntryAssociationParametersData associationParametersData);
    Page<JournalEntryData> retrieveAll(SearchParameters searchParameters, Long glAccountId, Boolean onlyManualEntries, LocalDate fromDate,
            LocalDate toDate, String transactionId, Integer entityType, JournalEntryAssociationParametersData associationParametersData);
    OfficeOpeningBalancesData retrieveOfficeOpeningBalances(Long officeId, String currencyCode);
    Page<JournalEntryData> retrieveJournalEntriesByEntityId(String transactionId, Long entityId, Integer entityType);
}
