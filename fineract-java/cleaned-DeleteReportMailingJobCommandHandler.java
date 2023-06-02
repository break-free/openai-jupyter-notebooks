
package org.apache.fineract.infrastructure.reportmailingjob.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.reportmailingjob.ReportMailingJobConstants;
import org.apache.fineract.infrastructure.reportmailingjob.service.ReportMailingJobWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = ReportMailingJobConstants.REPORT_MAILING_JOB_ENTITY_NAME, action = "DELETE")
public class DeleteReportMailingJobCommandHandler implements NewCommandSourceHandler {
    private final ReportMailingJobWritePlatformService reportMailingJobWritePlatformService;
    @Autowired
    public DeleteReportMailingJobCommandHandler(final ReportMailingJobWritePlatformService reportMailingJobWritePlatformService) {
        this.reportMailingJobWritePlatformService = reportMailingJobWritePlatformService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.reportMailingJobWritePlatformService.deleteReportMailingJob(jsonCommand.entityId());
    }
}
