
package org.apache.fineract.infrastructure.campaigns.email.data;
import java.time.LocalDate;
public class EmailCampaignTimeLine {
    private final LocalDate submittedOnDate;
    private final String submittedByUsername;
    private final LocalDate activatedOnDate;
    private final String activatedByUsername;
    private final LocalDate closedOnDate;
    private final String closedByUsername;
    public EmailCampaignTimeLine(final LocalDate submittedOnDate, final String submittedByUsername, final LocalDate activatedOnDate,
            final String activatedByUsername, final LocalDate closedOnDate, final String closedByUsername) {
        this.submittedOnDate = submittedOnDate;
        this.submittedByUsername = submittedByUsername;
        this.activatedOnDate = activatedOnDate;
        this.activatedByUsername = activatedByUsername;
        this.closedOnDate = closedOnDate;
        this.closedByUsername = closedByUsername;
    }
}
