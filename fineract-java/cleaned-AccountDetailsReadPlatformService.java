
package org.apache.fineract.portfolio.accountdetails.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.accountdetails.data.AccountSummaryCollectionData;
import org.apache.fineract.portfolio.accountdetails.data.LoanAccountSummaryData;
public interface AccountDetailsReadPlatformService {
    AccountSummaryCollectionData retrieveClientAccountDetails(Long clientId);
    AccountSummaryCollectionData retrieveGroupAccountDetails(Long groupId);
    Collection<LoanAccountSummaryData> retrieveClientLoanAccountsByLoanOfficerId(Long clientId, Long loanOfficerId);
    Collection<LoanAccountSummaryData> retrieveGroupLoanAccountsByLoanOfficerId(Long groupId, Long loanOfficerId);
    Collection<LoanAccountSummaryData> retrieveClientActiveLoanAccountSummary(Long clientId);
    List<LoanAccountSummaryData> retrieveLoanAccountDetailsByGroupIdAndGlimAccountNumber(Long groupId, String glimAccount);
    Collection<LoanAccountSummaryData> retrieveGroupActiveLoanAccountSummary(Long groupId);
    AccountSummaryCollectionData retrieveGroupAccountDetails(Long groupId, Long gsimId);
}
