
package org.apache.fineract.portfolio.loanaccount.guarantor.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GuarantorWritePlatformService {
    CommandProcessingResult createGuarantor(Long loanId, JsonCommand command);
    CommandProcessingResult updateGuarantor(Long loanId, Long guarantorId, JsonCommand command);
    CommandProcessingResult removeGuarantor(Long loanId, Long guarantorId, Long guarantorFundingId);
}
