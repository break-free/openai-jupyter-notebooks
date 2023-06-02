
package org.apache.fineract.organisation.teller.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.organisation.teller.data.CashierData;
import org.apache.fineract.organisation.teller.data.CashierTransactionData;
import org.apache.fineract.organisation.teller.data.CashierTransactionsWithSummaryData;
import org.apache.fineract.organisation.teller.data.TellerData;
import org.apache.fineract.organisation.teller.data.TellerJournalData;
import org.apache.fineract.organisation.teller.data.TellerTransactionData;
public interface TellerManagementReadPlatformService {
    Collection<TellerData> getTellers(Long officeId);
    TellerData findTeller(Long tellerId);
    CashierData findCashier(Long cashierId);
    Collection<CashierData> getCashierData(Long officeId, Long tellerId, Long staffId, LocalDate date);
    Collection<CashierData> getTellerCashiers(Long tellerId, LocalDate date);
    CashierData retrieveCashierTemplate(Long officeId, Long tellerId, boolean staffInSelectedOfficeOnly);
    CashierTransactionData retrieveCashierTxnTemplate(Long cashierId);
    TellerTransactionData findTellerTransaction(Long transactionId);
    Collection<TellerTransactionData> fetchTellerTransactionsByTellerId(Long tellerId, LocalDate fromDate, LocalDate toDate);
    Collection<TellerJournalData> getJournals(Long officeId, Long tellerId, Long cashierId, LocalDate dateFrom, LocalDate dateTo);
    Collection<TellerJournalData> fetchTellerJournals(Long tellerId, Long cashierId, LocalDate fromDate, LocalDate toDate);
    Collection<TellerData> retrieveAllTellersForDropdown(Long officeId);
    Collection<TellerData> retrieveAllTellers(String sqlSearch, Long officeId, String status);
    Collection<CashierData> getCashiersForTeller(Long tellerId, LocalDate fromDate, LocalDate toDate);
    Collection<CashierData> retrieveCashiersForTellers(String sqlSearch, Long tellerId);
    Page<CashierTransactionData> retrieveCashierTransactions(Long cashierId, boolean includeAllTellers, LocalDate fromDate,
            LocalDate toDate, String currencyCode, SearchParameters searchParameters);
    CashierTransactionsWithSummaryData retrieveCashierTransactionsWithSummary(Long cashierId, boolean includeAllTellers, LocalDate fromDate,
            LocalDate toDate, String currencyCode, SearchParameters searchParameters);
}
