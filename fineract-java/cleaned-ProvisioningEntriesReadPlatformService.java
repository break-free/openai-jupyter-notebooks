
package org.apache.fineract.accounting.provisioning.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.accounting.provisioning.data.LoanProductProvisioningEntryData;
import org.apache.fineract.accounting.provisioning.data.ProvisioningEntryData;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
public interface ProvisioningEntriesReadPlatformService {
    Collection<LoanProductProvisioningEntryData> retrieveLoanProductsProvisioningData(LocalDate date);
    ProvisioningEntryData retrieveProvisioningEntryData(Long entryId);
    Page<ProvisioningEntryData> retrieveAllProvisioningEntries(Integer offset, Integer limit);
    ProvisioningEntryData retrieveProvisioningEntryData(String date);
    ProvisioningEntryData retrieveProvisioningEntryDataByCriteriaId(Long criteriaId);
    ProvisioningEntryData retrieveExistingProvisioningIdDateWithJournals();
    Page<LoanProductProvisioningEntryData> retrieveProvisioningEntries(SearchParameters searchParams);
}
