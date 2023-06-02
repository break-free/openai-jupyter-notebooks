
package org.apache.fineract.infrastructure.survey.data;
import java.time.LocalDate;
public class ClientScoresOverview {
    @SuppressWarnings("unused")
    private final String surveyName;
    @SuppressWarnings("unused")
    private final long id;
    @SuppressWarnings("unused")
    private final String likelihoodCode;
    @SuppressWarnings("unused")
    private final String likelihoodName;
    @SuppressWarnings("unused")
    private final long score;
    @SuppressWarnings("unused")
    private final Double povertyLine;
    @SuppressWarnings("unused")
    private final LocalDate date;
    public ClientScoresOverview(final String likelihoodCode, final String likelihoodName, final long score, final Double povertyLine,
            final LocalDate date, final long resourceId, final String surveyName) {
        this.likelihoodCode = likelihoodCode;
        this.likelihoodName = likelihoodName;
        this.score = score;
        this.povertyLine = povertyLine;
        this.date = date;
        this.id = resourceId;
        this.surveyName = surveyName;
    }
}
