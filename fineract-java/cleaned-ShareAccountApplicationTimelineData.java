
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
import java.time.LocalDate;
@SuppressWarnings("unused")
public class ShareAccountApplicationTimelineData implements Serializable {
    private final LocalDate submittedOnDate;
    private final String submittedByUsername;
    private final String submittedByFirstname;
    private final String submittedByLastname;
    private final LocalDate rejectedDate;
    private final String rejectedByUsername;
    private final String rejectedByFirstname;
    private final String rejectedByLastname;
    private final LocalDate approvedDate;
    private final String approvedByUsername;
    private final String approvedByFirstname;
    private final String approvedByLastname;
    private final LocalDate activatedDate;
    private final String activatedByUsername;
    private final String activatedByFirstname;
    private final String activatedByLastname;
    private final LocalDate closedDate;
    private final String closedByUsername;
    private final String closedByFirstname;
    private final String closedByLastname;
    public static ShareAccountApplicationTimelineData templateDefault() {
        final LocalDate submittedOnDate = null;
        final String submittedByUsername = null;
        final String submittedByFirstname = null;
        final String submittedByLastname = null;
        final LocalDate rejectedOnDate = null;
        final String rejectedByUsername = null;
        final String rejectedByFirstname = null;
        final String rejectedByLastname = null;
        final LocalDate approvedOnDate = null;
        final String approvedByUsername = null;
        final String approvedByFirstname = null;
        final String approvedByLastname = null;
        final LocalDate activatedOnDate = null;
        final String activatedByUsername = null;
        final String activatedByFirstname = null;
        final String activatedByLastname = null;
        final LocalDate closedOnDate = null;
        final String closedByUsername = null;
        final String closedByFirstname = null;
        final String closedByLastname = null;
        return new ShareAccountApplicationTimelineData(submittedOnDate, submittedByUsername, submittedByFirstname, submittedByLastname,
                rejectedOnDate, rejectedByUsername, rejectedByFirstname, rejectedByLastname, approvedOnDate, approvedByUsername,
                approvedByFirstname, approvedByLastname, activatedOnDate, activatedByUsername, activatedByFirstname, activatedByLastname,
                closedOnDate, closedByUsername, closedByFirstname, closedByLastname);
    }
    public ShareAccountApplicationTimelineData(final LocalDate submittedOnDate, final String submittedByUsername,
            final String submittedByFirstname, final String submittedByLastname, final LocalDate rejectedOnDate,
            final String rejectedByUsername, final String rejectedByFirstname, final String rejectedByLastname,
            final LocalDate approvedOnDate, final String approvedByUsername, final String approvedByFirstname,
            final String approvedByLastname, final LocalDate activatedOnDate, final String activatedByUsername,
            final String activatedByFirstname, final String activatedByLastname, final LocalDate closedOnDate,
            final String closedByUsername, final String closedByFirstname, final String closedByLastname) {
        this.submittedOnDate = submittedOnDate;
        this.submittedByUsername = submittedByUsername;
        this.submittedByFirstname = submittedByFirstname;
        this.submittedByLastname = submittedByLastname;
        this.rejectedDate = rejectedOnDate;
        this.rejectedByUsername = rejectedByUsername;
        this.rejectedByFirstname = rejectedByFirstname;
        this.rejectedByLastname = rejectedByLastname;
        this.approvedDate = approvedOnDate;
        this.approvedByUsername = approvedByUsername;
        this.approvedByFirstname = approvedByFirstname;
        this.approvedByLastname = approvedByLastname;
        this.activatedDate = activatedOnDate;
        this.activatedByUsername = activatedByUsername;
        this.activatedByFirstname = activatedByFirstname;
        this.activatedByLastname = activatedByLastname;
        this.closedDate = closedOnDate;
        this.closedByUsername = closedByUsername;
        this.closedByFirstname = closedByFirstname;
        this.closedByLastname = closedByLastname;
    }
}
