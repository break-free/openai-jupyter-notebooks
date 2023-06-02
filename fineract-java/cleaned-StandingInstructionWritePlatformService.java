
package org.apache.fineract.portfolio.account.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface StandingInstructionWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long id, JsonCommand command);
    void executeStandingInstructions() throws JobExecutionException;
    CommandProcessingResult delete(Long id);
}
