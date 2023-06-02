
package org.apache.fineract.portfolio.loanaccount.loanschedule.service;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanRepaymentScheduleHistory;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain.LoanRescheduleRequest;
public interface LoanScheduleHistoryWritePlatformService {
    List<LoanRepaymentScheduleHistory> createLoanScheduleArchive(List<LoanRepaymentScheduleInstallment> repaymentScheduleInstallments,
            Loan loan, LoanRescheduleRequest loanRescheduleRequest);
    void createAndSaveLoanScheduleArchive(List<LoanRepaymentScheduleInstallment> repaymentScheduleInstallments, Loan loan,
            LoanRescheduleRequest loanRescheduleRequest);
}
