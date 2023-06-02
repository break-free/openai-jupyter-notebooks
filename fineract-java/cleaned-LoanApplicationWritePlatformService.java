
package org.apache.fineract.portfolio.loanaccount.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface LoanApplicationWritePlatformService {
    CommandProcessingResult submitApplication(JsonCommand command);
    CommandProcessingResult modifyApplication(Long loanId, JsonCommand command);
    CommandProcessingResult deleteApplication(Long loanId);
    CommandProcessingResult approveApplication(Long loanId, JsonCommand command);
    CommandProcessingResult undoApplicationApproval(Long loanId, JsonCommand command);
    CommandProcessingResult rejectApplication(Long loanId, JsonCommand command);
    CommandProcessingResult applicantWithdrawsFromApplication(Long loanId, JsonCommand command);
    CommandProcessingResult approveGLIMLoanAppication(Long loanId, JsonCommand command);
    CommandProcessingResult undoGLIMLoanApplicationApproval(Long loanId, JsonCommand command);
    CommandProcessingResult rejectGLIMApplicationApproval(Long loanId, JsonCommand command);
}
