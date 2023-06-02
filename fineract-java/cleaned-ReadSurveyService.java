
package org.apache.fineract.infrastructure.survey.service;
import java.util.List;
import org.apache.fineract.infrastructure.dataqueries.data.GenericResultsetData;
import org.apache.fineract.infrastructure.survey.data.ClientScoresOverview;
import org.apache.fineract.infrastructure.survey.data.SurveyDataTableData;
public interface ReadSurveyService {
    List<SurveyDataTableData> retrieveAllSurveys();
    SurveyDataTableData retrieveSurvey(String surveyName);
    List<ClientScoresOverview> retrieveClientSurveyScoreOverview(String surveyName, Long clientId);
    List<ClientScoresOverview> retrieveClientSurveyScoreOverview(Long clientId);
    GenericResultsetData retrieveSurveyEntry(String surveyName, Long clientId, Long entryId);
}
