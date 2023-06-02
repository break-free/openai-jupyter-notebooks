
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import java.util.Map;
import org.apache.fineract.infrastructure.core.data.PaginationParameters;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.portfolio.account.data.AccountTransferDTO;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.data.DepositAccountData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionData;
public interface DepositAccountReadPlatformService {
    Collection<DepositAccountData> retrieveAll(DepositAccountType depositAccountType, PaginationParameters paginationParameters);
    Page<DepositAccountData> retrieveAllPaged(DepositAccountType depositAccountType, PaginationParameters paginationParameters);
    Collection<DepositAccountData> retrieveAllForLookup(DepositAccountType depositAccountType);
    DepositAccountData retrieveOne(DepositAccountType depositAccountType, Long accountId);
    DepositAccountData retrieveOneWithClosureTemplate(DepositAccountType depositAccountType, Long accountId);
    DepositAccountData retrieveOneWithChartSlabs(DepositAccountType depositAccountType, Long productId);
    Collection<SavingsAccountTransactionData> retrieveAllTransactions(DepositAccountType depositAccountType, Long accountId);
    DepositAccountData retrieveTemplate(DepositAccountType depositAccountType, Long clientId, Long groupId, Long productId,
            boolean staffInSelectedOfficeOnly);
    Collection<DepositAccountData> retrieveForMaturityUpdate();
    SavingsAccountTransactionData retrieveRecurringAccountDepositTransactionTemplate(Long accountId);
    Collection<AccountTransferDTO> retrieveDataForInterestTransfer();
    Collection<Map<String, Object>> retriveDataForRDScheduleCreation();
}
