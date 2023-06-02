
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Set;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.portfolio.loanaccount.data.HolidayDetailDTO;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.LoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleDTO;
public interface LoanScheduleGenerator {
    LoanScheduleModel generate(MathContext mc, LoanApplicationTerms loanApplicationTerms, Set<LoanCharge> loanCharges,
            HolidayDetailDTO holidayDetailDTO);
    LoanScheduleDTO rescheduleNextInstallments(MathContext mc, LoanApplicationTerms loanApplicationTerms, Loan loan,
            HolidayDetailDTO holidayDetailDTO, LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor,
            LocalDate rescheduleFrom);
    LoanRepaymentScheduleInstallment calculatePrepaymentAmount(MonetaryCurrency currency, LocalDate onDate,
            LoanApplicationTerms loanApplicationTerms, MathContext mc, Loan loan, HolidayDetailDTO holidayDetailDTO,
            LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor);
}
