
package org.apache.fineract.spm.util;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.spm.data.ScorecardData;
import org.apache.fineract.spm.data.ScorecardValue;
import org.apache.fineract.spm.domain.Question;
import org.apache.fineract.spm.domain.Response;
import org.apache.fineract.spm.domain.Scorecard;
import org.apache.fineract.spm.domain.Survey;
import org.apache.fineract.spm.exception.SurveyResponseNotAvailableException;
import org.apache.fineract.useradministration.domain.AppUser;
public final class ScorecardMapper {
    private ScorecardMapper() {
    }
    public static List<Scorecard> map(final ScorecardData scorecardData, final Survey survey, final AppUser appUser, final Client client) {
        final List<Scorecard> scorecards = new ArrayList<>();
        final List<ScorecardValue> scorecardValues = scorecardData.getScorecardValues();
        if (scorecardValues != null && !scorecardValues.isEmpty()) {
            for (ScorecardValue scorecardValue : scorecardValues) {
                final Scorecard scorecard = new Scorecard();
                scorecards.add(scorecard);
                scorecard.setSurvey(survey);
                ScorecardMapper.setQuestionAndResponse(scorecardValue, scorecard, survey);
                scorecard.setAppUser(appUser);
                scorecard.setClient(client);
                scorecard.setCreatedOn(DateUtils.getLocalDateTimeOfTenant());
                scorecard.setValue(scorecardValue.getValue());
            }
        } else {
            throw new SurveyResponseNotAvailableException();
        }
        return scorecards;
    }
    private static void setQuestionAndResponse(final ScorecardValue scorecardValue, final Scorecard scorecard, final Survey survey) {
        final List<Question> questions = survey.getQuestions();
        for (final Question question : questions) {
            if (question.getId().equals(scorecardValue.getQuestionId())) {
                scorecard.setQuestion(question);
                for (final Response response : question.getResponses()) {
                    if (response.getId().equals(scorecardValue.getResponseId())) {
                        scorecard.setResponse(response);
                        break;
                    }
                }
                break;
            }
        }
    }
}
