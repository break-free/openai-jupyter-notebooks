
package org.apache.fineract.spm.domain;
import java.util.List;
import org.apache.fineract.portfolio.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {
    List<Scorecard> findBySurvey(Survey survey);
    List<Scorecard> findBySurveyAndClient(Survey survey, Client client);
}
