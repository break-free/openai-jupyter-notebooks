
package org.apache.fineract.infrastructure.reportmailingjob.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobData;
public interface ReportMailingJobReadPlatformService {
    Page<ReportMailingJobData> retrieveAllReportMailingJobs(SearchParameters searchParameters);
    ReportMailingJobData retrieveReportMailingJob(Long reportMailingJobId);
    ReportMailingJobData retrieveReportMailingJobEnumOptions();
    Collection<ReportMailingJobData> retrieveAllActiveReportMailingJobs();
}
