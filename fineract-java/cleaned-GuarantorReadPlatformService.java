
package org.apache.fineract.portfolio.loanaccount.guarantor.service;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.guarantor.data.GuarantorData;
import org.apache.fineract.portfolio.loanaccount.guarantor.data.ObligeeData;
public interface GuarantorReadPlatformService {
    List<GuarantorData> retrieveGuarantorsForValidLoan(Long loanId);
    List<GuarantorData> retrieveGuarantorsForLoan(Long loanId);
    GuarantorData retrieveGuarantor(Long loanId, Long guarantorId);
    List<ObligeeData> retrieveObligeeDetails(Long clientId);
}
