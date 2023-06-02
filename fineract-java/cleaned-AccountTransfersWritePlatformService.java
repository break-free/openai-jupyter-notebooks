
package org.apache.fineract.portfolio.account.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.account.PortfolioAccountType;
import org.apache.fineract.portfolio.account.data.AccountTransferDTO;
import org.apache.fineract.portfolio.account.domain.AccountTransferDetails;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public interface AccountTransfersWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    void reverseTransfersWithFromAccountType(Long accountNumber, PortfolioAccountType accountTypeId);
    Long transferFunds(AccountTransferDTO accountTransferDTO);
    void reverseAllTransactions(Long accountId, PortfolioAccountType accountTypeId);
    void updateLoanTransaction(Long loanTransactionId, LoanTransaction newLoanTransaction);
    CommandProcessingResult refundByTransfer(JsonCommand command);
    void reverseTransfersWithFromAccountTransactions(Collection<Long> fromTransactionIds, PortfolioAccountType accountTypeId);
    AccountTransferDetails repayLoanWithTopup(AccountTransferDTO accountTransferDTO);
}
