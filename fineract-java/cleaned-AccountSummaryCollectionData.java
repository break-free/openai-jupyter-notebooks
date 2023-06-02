
package org.apache.fineract.portfolio.accountdetails.data;
import java.util.Collection;
@SuppressWarnings("unused")
public class AccountSummaryCollectionData {
    private final Collection<LoanAccountSummaryData> loanAccounts;
    private final Collection<LoanAccountSummaryData> groupLoanIndividualMonitoringAccounts;
    private final Collection<SavingsAccountSummaryData> savingsAccounts;
    private final Collection<ShareAccountSummaryData> shareAccounts;
    private final Collection<GuarantorAccountSummaryData> guarantorAccounts;
    private final Collection<LoanAccountSummaryData> memberLoanAccounts;
    private final Collection<SavingsAccountSummaryData> memberSavingsAccounts;
    private final Collection<GuarantorAccountSummaryData> memberGuarantorAccounts;
    public AccountSummaryCollectionData(final Collection<LoanAccountSummaryData> loanAccounts,
            final Collection<LoanAccountSummaryData> groupLoanIndividualMonitoringAccounts,
            final Collection<SavingsAccountSummaryData> savingsAccounts, final Collection<ShareAccountSummaryData> shareAccounts,
            final Collection<GuarantorAccountSummaryData> guarantorAccounts) {
        this.loanAccounts = defaultLoanAccountsIfEmpty(loanAccounts);
        this.groupLoanIndividualMonitoringAccounts = groupLoanIndividualMonitoringAccounts;
        this.savingsAccounts = defaultSavingsAccountsIfEmpty(savingsAccounts);
        this.shareAccounts = defaultShareAccountsIfEmpty(shareAccounts);
        this.guarantorAccounts = guarantorAccounts;
        this.memberLoanAccounts = null;
        this.memberSavingsAccounts = null;
        this.memberGuarantorAccounts = null;
    }
    public AccountSummaryCollectionData(final Collection<LoanAccountSummaryData> loanAccounts,
            final Collection<LoanAccountSummaryData> groupLoanIndividualMonitoringAccounts,
            final Collection<SavingsAccountSummaryData> savingsAccounts, final Collection<GuarantorAccountSummaryData> guarantorAccounts,
            final Collection<LoanAccountSummaryData> memberLoanAccounts, final Collection<SavingsAccountSummaryData> memberSavingsAccounts,
            final Collection<GuarantorAccountSummaryData> memberGuarantorAccounts) {
        this.loanAccounts = defaultLoanAccountsIfEmpty(loanAccounts);
        this.groupLoanIndividualMonitoringAccounts = groupLoanIndividualMonitoringAccounts;
        this.savingsAccounts = defaultSavingsAccountsIfEmpty(savingsAccounts);
        this.guarantorAccounts = guarantorAccounts;
        this.shareAccounts = null;
        this.memberLoanAccounts = defaultLoanAccountsIfEmpty(memberLoanAccounts);
        this.memberSavingsAccounts = defaultSavingsAccountsIfEmpty(memberSavingsAccounts);
        this.memberGuarantorAccounts = defaultGuarantorAccountsIfEmpty(memberGuarantorAccounts);
    }
    private Collection<LoanAccountSummaryData> defaultLoanAccountsIfEmpty(final Collection<LoanAccountSummaryData> collection) {
        Collection<LoanAccountSummaryData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }
    private Collection<SavingsAccountSummaryData> defaultSavingsAccountsIfEmpty(final Collection<SavingsAccountSummaryData> collection) {
        Collection<SavingsAccountSummaryData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }
    private Collection<ShareAccountSummaryData> defaultShareAccountsIfEmpty(final Collection<ShareAccountSummaryData> collection) {
        Collection<ShareAccountSummaryData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }
    private Collection<GuarantorAccountSummaryData> defaultGuarantorAccountsIfEmpty(
            final Collection<GuarantorAccountSummaryData> collection) {
        Collection<GuarantorAccountSummaryData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }
}
