
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.data;
import java.time.LocalDate;
@SuppressWarnings("unused")
public class LoanRescheduleRequestTimelineData {
    private final LocalDate submittedOnDate;
    private final String submittedByUsername;
    private final String submittedByFirstname;
    private final String submittedByLastname;
    private final LocalDate approvedOnDate;
    private final String approvedByUsername;
    private final String approvedByFirstname;
    private final String approvedByLastname;
    private final LocalDate rejectedOnDate;
    private final String rejectedByUsername;
    private final String rejectedByFirstname;
    private final String rejectedByLastname;
    public LoanRescheduleRequestTimelineData(final LocalDate submittedOnDate, final String submittedByUsername,
            final String submittedByFirstname, final String submittedByLastname, final LocalDate approvedOnDate,
            final String approvedByUsername, final String approvedByFirstname, final String approvedByLastname,
            final LocalDate rejectedOnDate, final String rejectedByUsername, final String rejectedByFirstname,
            final String rejectedByLastname) {
        this.submittedOnDate = submittedOnDate;
        this.submittedByUsername = submittedByUsername;
        this.submittedByFirstname = submittedByFirstname;
        this.submittedByLastname = submittedByLastname;
        this.approvedOnDate = approvedOnDate;
        this.approvedByUsername = approvedByUsername;
        this.approvedByFirstname = approvedByFirstname;
        this.approvedByLastname = approvedByLastname;
        this.rejectedOnDate = rejectedOnDate;
        this.rejectedByUsername = rejectedByUsername;
        this.rejectedByFirstname = rejectedByFirstname;
        this.rejectedByLastname = rejectedByLastname;
    }
}
