
package org.apache.fineract.portfolio.group.data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
@SuppressWarnings("unused")
public class GroupTimelineData implements Serializable {
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
    public GroupTimelineData(final LocalDate submittedOnDate, final String submittedByUsername, final String submittedByFirstname,
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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupTimelineData)) {
            return false;
        }
        GroupTimelineData that = (GroupTimelineData) o;
        return Objects.equals(submittedOnDate, that.submittedOnDate) && Objects.equals(submittedByUsername, that.submittedByUsername)
                && Objects.equals(submittedByFirstname, that.submittedByFirstname)
                && Objects.equals(submittedByLastname, that.submittedByLastname) && Objects.equals(activatedOnDate, that.activatedOnDate)
                && Objects.equals(activatedByUsername, that.activatedByUsername)
                && Objects.equals(activatedByFirstname, that.activatedByFirstname)
                && Objects.equals(activatedByLastname, that.activatedByLastname) && Objects.equals(closedOnDate, that.closedOnDate)
                && Objects.equals(closedByUsername, that.closedByUsername) && Objects.equals(closedByFirstname, that.closedByFirstname)
                && Objects.equals(closedByLastname, that.closedByLastname);
    }
    @Override
    public int hashCode() {
        return Objects.hash(submittedOnDate, submittedByUsername, submittedByFirstname, submittedByLastname, activatedOnDate,
                activatedByUsername, activatedByFirstname, activatedByLastname, closedOnDate, closedByUsername, closedByFirstname,
                closedByLastname);
    }
}
