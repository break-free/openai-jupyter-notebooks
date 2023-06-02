
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
import org.apache.fineract.portfolio.savings.SavingsTransactionBooleanValues;
import org.apache.fineract.useradministration.domain.AppUser;
public interface SavingsAccountDomainService {
    SavingsAccountTransaction handleWithdrawal(SavingsAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, SavingsTransactionBooleanValues transactionBooleanValues,
            boolean backdatedTxnsAllowedTill);
    SavingsAccountTransaction handleDeposit(SavingsAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, boolean isAccountTransfer, boolean isRegularTransaction,
            boolean backdatedTxnsAllowedTill);
    void postJournalEntries(SavingsAccount savingsAccount, Set<Long> existingTransactionIds, Set<Long> existingReversedTransactionIds,
            boolean backdatedTxnsAllowedTill);
    SavingsAccountTransaction handleDividendPayout(SavingsAccount account, LocalDate transactionDate, BigDecimal transactionAmount,
            boolean backdatedTxnsAllowedTill);
    SavingsAccountTransaction handleReversal(SavingsAccount account, List<SavingsAccountTransaction> savingsAccountTransactions,
            boolean backdatedTxnsAllowedTill);
    SavingsAccountTransaction handleHold(SavingsAccount account, AppUser createdUser, BigDecimal amount, LocalDate transactionDate,
            Boolean lienAllowed);
}
