
package org.apache.fineract.portfolio.loanaccount.guarantor.service;
import java.time.LocalDate;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.guarantor.domain.GuarantorFundingDetails;
public interface GuarantorDomainService {
    void releaseGuarantor(GuarantorFundingDetails guarantorFundingDetails, LocalDate transactionDate);
    void validateGuarantorBusinessRules(Loan loan);
    void assignGuarantor(GuarantorFundingDetails guarantorFundingDetails, LocalDate transactionDate);
    void transaferFundsFromGuarantor(Loan loan);
}
