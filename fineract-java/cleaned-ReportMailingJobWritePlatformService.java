
package org.apache.fineract.infrastructure.reportmailingjob.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface ReportMailingJobWritePlatformService {
    CommandProcessingResult createReportMailingJob(JsonCommand jsonCommand);
    CommandProcessingResult updateReportMailingJob(Long reportMailingJobId, JsonCommand jsonCommand);
    CommandProcessingResult deleteReportMailingJob(Long reportMailingJobId);
    void executeReportMailingJobs() throws JobExecutionException;
}
