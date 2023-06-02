
package org.apache.fineract.portfolio.client.data;
import java.io.Serializable;
import java.time.LocalDate;
@SuppressWarnings("unused")
public class ClientTimelineData implements Serializable {
    private final LocalDate submittedOnDate;
    private final String submittedByUsername;
    private final String submittedByFirstname;
    private final String submittedByLastname;
    private final LocalDate activatedOnDate;
    private final String activatedByUsername;
    private final String activatedByFirstname;
    private final String activatedByLastname;
    private final LocalDate closedOnDate;
    private final String closedByUsername;
    private final String closedByFirstname;
    private final String closedByLastname;
    public ClientTimelineData(final LocalDate submittedOnDate, final String submittedByUsername, final String submittedByFirstname,
            final String submittedByLastname, final LocalDate activatedOnDate, final String activatedByUsername,
            final String activatedByFirstname, final String activatedByLastname, final LocalDate closedOnDate,
            final String closedByUsername, final String closedByFirstname, final String closedByLastname) {
        this.submittedOnDate = submittedOnDate;
        this.submittedByUsername = submittedByUsername;
        this.submittedByFirstname = submittedByFirstname;
        this.submittedByLastname = submittedByLastname;
        this.activatedOnDate = activatedOnDate;
        this.activatedByUsername = activatedByUsername;
        this.activatedByFirstname = activatedByFirstname;
        this.activatedByLastname = activatedByLastname;
        this.closedOnDate = closedOnDate;
        this.closedByUsername = closedByUsername;
        this.closedByFirstname = closedByFirstname;
        this.closedByLastname = closedByLastname;
    }
}
