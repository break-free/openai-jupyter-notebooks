
package org.apache.fineract.portfolio.loanaccount.service;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public interface LoanArrearsAgingService {
    void updateLoanArrearsAgeingDetails();
    void updateLoanArrearsAgeingDetailsWithOriginalSchedule(Loan loan);
    void updateLoanArrearsAgeingDetails(Loan loan);
}
