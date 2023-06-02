
package org.apache.fineract.infrastructure.businessdate.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface BusinessDateWritePlatformService {
    CommandProcessingResult updateBusinessDate(JsonCommand command);
    void increaseCOBDateByOneDay() throws JobExecutionException;
    void increaseBusinessDateByOneDay() throws JobExecutionException;
}
