
package org.apache.fineract.infrastructure.reportmailingjob.data;
import java.time.LocalDate;
@SuppressWarnings("unused")
public class ReportMailingJobTimelineData {
    private final LocalDate createdOnDate;
    private final String createdByUsername;
    private final String createdByFirstname;
    private final String createdByLastname;
    private final LocalDate updatedOnDate;
    private final String updatedByUsername;
    private final String updatedByFirstname;
    private final String updatedByLastname;
    public ReportMailingJobTimelineData(LocalDate createdOnDate, String createdByUsername, String createdByFirstname,
            String createdByLastname, LocalDate updatedOnDate, String updatedByUsername, String updatedByFirstname,
            String updatedByLastname) {
        this.createdOnDate = createdOnDate;
        this.createdByUsername = createdByUsername;
        this.createdByFirstname = createdByFirstname;
        this.createdByLastname = createdByLastname;
        this.updatedOnDate = updatedOnDate;
        this.updatedByUsername = updatedByUsername;
        this.updatedByFirstname = updatedByFirstname;
        this.updatedByLastname = updatedByLastname;
    }
}
