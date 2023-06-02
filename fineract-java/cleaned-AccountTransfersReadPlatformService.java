
package org.apache.fineract.portfolio.account.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.account.PortfolioAccountType;
import org.apache.fineract.portfolio.account.data.AccountTransferData;
public interface AccountTransfersReadPlatformService {
    AccountTransferData retrieveTemplate(Long fromOfficeId, Long fromClientId, Long fromAccountId, Integer fromAccountType, Long toOfficeId,
            Long toClientId, Long toAccountId, Integer toAccountType);
    Page<AccountTransferData> retrieveAll(SearchParameters searchParameters, Long accountDetailId);
    AccountTransferData retrieveOne(Long transferId);
    boolean isAccountTransfer(Long transactionId, PortfolioAccountType accountType);
    Page<AccountTransferData> retrieveByStandingInstruction(Long id, SearchParameters searchParameters);
    Collection<Long> fetchPostInterestTransactionIds(Long accountId);
    Collection<Long> fetchPostInterestTransactionIdsWithPivotDate(Long accountId, LocalDate pivotDate);
    AccountTransferData retrieveRefundByTransferTemplate(Long fromOfficeId, Long fromClientId, Long fromAccountId, Integer fromAccountType,
            Long toOfficeId, Long toClientId, Long toAccountId, Integer toAccountType);
    BigDecimal getTotalTransactionAmount(Long accountId, Integer accountType, LocalDate transactionDate);
}
