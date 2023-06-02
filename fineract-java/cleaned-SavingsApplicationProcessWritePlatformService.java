
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.data.SavingsAccountDataDTO;
public interface SavingsApplicationProcessWritePlatformService {
    CommandProcessingResult submitApplication(JsonCommand command);
    CommandProcessingResult modifyApplication(Long savingsId, JsonCommand command);
    CommandProcessingResult deleteApplication(Long savingsId);
    CommandProcessingResult approveApplication(Long savingsId, JsonCommand command);
    CommandProcessingResult undoApplicationApproval(Long savingsId, JsonCommand command);
    CommandProcessingResult rejectApplication(Long savingsId, JsonCommand command);
    CommandProcessingResult applicantWithdrawsFromApplication(Long savingsId, JsonCommand command);
    CommandProcessingResult createActiveApplication(SavingsAccountDataDTO savingsAccountDataDTO);
    CommandProcessingResult submitGSIMApplication(JsonCommand command);
    CommandProcessingResult approveGSIMApplication(Long gsimId, JsonCommand command);
    CommandProcessingResult rejectGSIMApplication(Long gsimId, JsonCommand command);
    CommandProcessingResult undoGSIMApplicationApproval(Long gsimId, JsonCommand command);
    CommandProcessingResult modifyGSIMApplication(Long gsimId, JsonCommand command);
}
