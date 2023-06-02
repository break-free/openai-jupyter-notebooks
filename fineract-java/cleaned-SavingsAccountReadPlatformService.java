
package org.apache.fineract.portfolio.savings.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.data.SavingsAccountData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionData;
public interface SavingsAccountReadPlatformService {
    Page<SavingsAccountData> retrieveAll(SearchParameters searchParameters);
    Collection<SavingsAccountData> retrieveAllForLookup(Long clientId);
    Collection<SavingsAccountData> retrieveActiveForLookup(Long clientId, DepositAccountType depositAccountType);
    Collection<SavingsAccountData> retrieveActiveForLookup(Long clientId, DepositAccountType depositAccountType, String currencyCode);
    SavingsAccountData retrieveOne(Long savingsId);
    SavingsAccountData retrieveTemplate(Long clientId, Long groupId, Long productId, boolean staffInSelectedOfficeOnly);
    SavingsAccountTransactionData retrieveDepositTransactionTemplate(Long savingsId, DepositAccountType depositAccountType);
    Collection<SavingsAccountTransactionData> retrieveAllTransactions(Long savingsId, DepositAccountType depositAccountType);
    SavingsAccountTransactionData retrieveSavingsTransaction(Long savingsId, Long transactionId, DepositAccountType depositAccountType);
    Collection<SavingsAccountData> retrieveForLookup(Long clientId, Boolean overdraft);
    List<Long> retrieveSavingsIdsPendingInactive(LocalDate tenantLocalDate);
    List<Long> retrieveSavingsIdsPendingDormant(LocalDate tenantLocalDate);
    List<Long> retrieveSavingsIdsPendingEscheat(LocalDate tenantLocalDate);
    boolean isAccountBelongsToClient(Long clientId, Long accountId, DepositAccountType depositAccountType, String currencyCode);
    String retrieveAccountNumberByAccountId(Long accountId);
    List<Long> getAccountsIdsByStatusPaged(Integer status, int pageSize, Long maxSavingsIdInList);
    List<SavingsAccountData> retrieveAllSavingsDataForInterestPosting(boolean backdatedTxnsAllowedTill, int pageSize, Integer status,
            Long maxSavingsId);
    List<SavingsAccountTransactionData> retrieveAllTransactionData(List<String> refNo);
}
