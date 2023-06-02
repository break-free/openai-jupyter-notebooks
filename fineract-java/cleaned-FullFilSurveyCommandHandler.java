
package org.apache.fineract.infrastructure.survey.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.survey.service.WriteSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class FullFilSurveyCommandHandler implements NewCommandSourceHandler {
    private final WriteSurveyService writePlatformService;
    @Autowired
    public FullFilSurveyCommandHandler(final WriteSurveyService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.fullFillSurvey(command.entityName(), command.entityId(), command);
    }
}
