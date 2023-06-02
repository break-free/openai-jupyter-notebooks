
package org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.loanaccount.domain.ChangedTransactionDetail;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public interface LoanRepaymentScheduleTransactionProcessor {
    void handleTransaction(LoanTransaction loanTransaction, MonetaryCurrency currency, List<LoanRepaymentScheduleInstallment> installments,
            Set<LoanCharge> charges);
    ChangedTransactionDetail handleTransaction(LocalDate disbursementDate, List<LoanTransaction> repaymentsOrWaivers,
            MonetaryCurrency currency, List<LoanRepaymentScheduleInstallment> repaymentScheduleInstallments, Set<LoanCharge> charges);
    void handleWriteOff(LoanTransaction loanTransaction, MonetaryCurrency loanCurrency,
            List<LoanRepaymentScheduleInstallment> repaymentScheduleInstallments);
    Money handleRepaymentSchedule(List<LoanTransaction> transactionsPostDisbursement, MonetaryCurrency currency,
            List<LoanRepaymentScheduleInstallment> installments);
    boolean isInterestFirstRepaymentScheduleTransactionProcessor();
    void handleRefund(LoanTransaction loanTransaction, MonetaryCurrency currency, List<LoanRepaymentScheduleInstallment> installments,
            Set<LoanCharge> charges);
    void processTransactionsFromDerivedFields(List<LoanTransaction> transactionsPostDisbursement, MonetaryCurrency currency,
            List<LoanRepaymentScheduleInstallment> installments, Set<LoanCharge> charges);
}
