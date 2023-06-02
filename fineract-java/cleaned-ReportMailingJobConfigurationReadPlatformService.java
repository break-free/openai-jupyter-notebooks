
package org.apache.fineract.infrastructure.reportmailingjob.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobConfigurationData;
public interface ReportMailingJobConfigurationReadPlatformService {
    Collection<ReportMailingJobConfigurationData> retrieveAllReportMailingJobConfigurations();
    ReportMailingJobConfigurationData retrieveReportMailingJobConfiguration(String name);
}
