
package org.apache.fineract.spm.service;
import java.util.List;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.spm.domain.Scorecard;
import org.apache.fineract.spm.domain.ScorecardRepository;
import org.apache.fineract.spm.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ScorecardService {
    private final PlatformSecurityContext securityContext;
    private final ScorecardRepository scorecardRepository;
    @Autowired
    public ScorecardService(final PlatformSecurityContext securityContext, final ScorecardRepository scorecardRepository) {
        this.securityContext = securityContext;
        this.scorecardRepository = scorecardRepository;
    }
    public List<Scorecard> createScorecard(final List<Scorecard> scorecards) {
        this.securityContext.authenticatedUser();
        return this.scorecardRepository.saveAll(scorecards);
    }
    public List<Scorecard> findBySurvey(final Survey survey) {
        this.securityContext.authenticatedUser();
        return this.scorecardRepository.findBySurvey(survey);
    }
    public List<Scorecard> findBySurveyAndClient(final Survey survey, final Client client) {
        this.securityContext.authenticatedUser();
        return this.scorecardRepository.findBySurveyAndClient(survey, client);
    }
}
