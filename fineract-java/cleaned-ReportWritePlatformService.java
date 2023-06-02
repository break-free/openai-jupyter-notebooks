
package org.apache.fineract.infrastructure.dataqueries.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ReportWritePlatformService {
    CommandProcessingResult createReport(JsonCommand command);
    CommandProcessingResult updateReport(Long reportId, JsonCommand command);
    CommandProcessingResult deleteReport(Long reportId);
}
