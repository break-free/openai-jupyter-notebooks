
package org.apache.fineract.spm.service;
import java.util.Collection;
import org.apache.fineract.spm.data.ScorecardData;
public interface ScorecardReadPlatformService {
    Collection<ScorecardData> retrieveScorecardByClient(Long clientId);
    Collection<ScorecardData> retrieveScorecardBySurveyAndClient(Long surveyId, Long clientId);
    Collection<ScorecardData> retrieveScorecardBySurvey(Long surveyId);
}
