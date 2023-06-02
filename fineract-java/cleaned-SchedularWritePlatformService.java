
package org.apache.fineract.infrastructure.jobs.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.domain.ScheduledJobDetail;
import org.apache.fineract.infrastructure.jobs.domain.ScheduledJobRunHistory;
import org.apache.fineract.infrastructure.jobs.domain.SchedulerDetail;
public interface SchedularWritePlatformService {
    List<ScheduledJobDetail> retrieveAllJobs(String nodeId);
    ScheduledJobDetail findByJobKey(String triggerKey);
    void saveOrUpdate(ScheduledJobDetail scheduledJobDetails);
    void saveOrUpdate(ScheduledJobDetail scheduledJobDetails, ScheduledJobRunHistory scheduledJobRunHistory);
    Long fetchMaxVersionBy(String triggerKey);
    ScheduledJobDetail findByJobId(Long jobId);
    CommandProcessingResult updateJobDetail(Long jobId, JsonCommand command);
    SchedulerDetail retriveSchedulerDetail();
    void updateSchedulerDetail(SchedulerDetail schedulerDetail);
    boolean processJobDetailForExecution(String jobKey, String triggerType);
}
