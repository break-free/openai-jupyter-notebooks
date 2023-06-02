
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface LoanRescheduleRequestWritePlatformService {
    CommandProcessingResult create(JsonCommand jsonCommand);
    CommandProcessingResult approve(JsonCommand jsonCommand);
    CommandProcessingResult reject(JsonCommand jsonCommand);
}
