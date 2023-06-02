
package org.apache.fineract.portfolio.loanaccount.loanschedule.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface LoanScheduleWritePlatformService {
    CommandProcessingResult addLoanScheduleVariations(Long loanId, JsonCommand command);
    CommandProcessingResult deleteLoanScheduleVariations(Long loanId);
}
