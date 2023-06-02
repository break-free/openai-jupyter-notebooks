
package org.apache.fineract.infrastructure.jobs.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.jobs.data.JobDetailData;
import org.apache.fineract.infrastructure.jobs.data.JobDetailHistoryData;
public interface SchedulerJobRunnerReadService {
    List<JobDetailData> findAllJobDeatils();
    JobDetailData retrieveOne(Long jobId);
    Page<JobDetailHistoryData> retrieveJobHistory(Long jobId, SearchParameters searchParameters);
    boolean isUpdatesAllowed();
}
