
package org.apache.fineract.portfolio.loanaccount.service;
import org.apache.fineract.organisation.staff.data.StaffAccountSummaryCollectionData;
public interface BulkLoansReadPlatformService {
    StaffAccountSummaryCollectionData retrieveLoanOfficerAccountSummary(Long loanOfficerId);
}
