
package org.apache.fineract.portfolio.savings.service;
import java.math.MathContext;
import java.time.LocalDate;
import org.apache.fineract.portfolio.savings.data.SavingsAccountData;
public interface SavingsAccountInterestPostingService {
    SavingsAccountData postInterest(MathContext mc, LocalDate interestPostingUpToDate, boolean isInterestTransfer,
            boolean isSavingsInterestPostingAtCurrentPeriodEnd, Integer financialYearBeginningMonth, LocalDate postInterestOnDate,
            boolean backdatedTxnsAllowedTill, SavingsAccountData savingsAccountData);
}
