
package org.apache.fineract.infrastructure.reportmailingjob.service;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobRunHistoryData;
public interface ReportMailingJobRunHistoryReadPlatformService {
    Page<ReportMailingJobRunHistoryData> retrieveRunHistoryByJobId(Long reportMailingJobId, SearchParameters searchParameters);
}
