
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.DepositAccountType;
public interface DepositApplicationProcessWritePlatformService {
    CommandProcessingResult submitFDApplication(JsonCommand command);
    CommandProcessingResult submitRDApplication(JsonCommand command);
    CommandProcessingResult modifyFDApplication(Long accountId, JsonCommand command);
    CommandProcessingResult modifyRDApplication(Long accountId, JsonCommand command);
    CommandProcessingResult deleteApplication(Long accountId, DepositAccountType depositAccountType);
    CommandProcessingResult approveApplication(Long accountId, JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult undoApplicationApproval(Long accountId, JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult rejectApplication(Long accountId, JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult applicantWithdrawsFromApplication(Long accountId, JsonCommand command, DepositAccountType depositAccountType);
}
