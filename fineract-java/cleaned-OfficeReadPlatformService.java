
package org.apache.fineract.organisation.office.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.organisation.office.data.OfficeData;
import org.apache.fineract.organisation.office.data.OfficeTransactionData;
public interface OfficeReadPlatformService {
    Collection<OfficeData> retrieveAllOffices(boolean includeAllOffices, SearchParameters searchParameters);
    Collection<OfficeData> retrieveAllOfficesForDropdown();
    OfficeData retrieveOffice(Long officeId);
    OfficeData retrieveNewOfficeTemplate();
    Collection<OfficeData> retrieveAllowedParents(Long officeId);
    Collection<OfficeTransactionData> retrieveAllOfficeTransactions();
    OfficeTransactionData retrieveNewOfficeTransactionDetails();
}
