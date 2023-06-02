
package org.apache.fineract.infrastructure.survey.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface WriteSurveyService {
    CommandProcessingResult registerSurvey(JsonCommand command);
    CommandProcessingResult fullFillSurvey(String datatable, Long appTableId, JsonCommand command);
}
