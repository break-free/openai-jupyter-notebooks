
package org.apache.fineract.portfolio.group.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.organisation.staff.domain.Staff;
@Entity
@Table(name = "m_staff_assignment_history")
public class StaffAssignmentHistory extends AbstractAuditableCustom {
    @ManyToOne
    @JoinColumn(name = "centre_id", nullable = true)
    private Group center;
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = true)
    private Staff staff;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    public static StaffAssignmentHistory createNew(final Group center, final Staff staff, final LocalDate startDate) {
        return new StaffAssignmentHistory(center, staff, startDate, null);
    }
    protected StaffAssignmentHistory() {
    }
    private StaffAssignmentHistory(final Group center, final Staff staff, final LocalDate startDate, final LocalDate endDate) {
        this.center = center;
        this.staff = staff;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void updateStaff(final Staff staff) {
        this.staff = staff;
    }
    public void updateStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }
    public void updateEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }
    public boolean matchesStartDateOf(final LocalDate matchingDate) {
        return getStartDate().isEqual(matchingDate);
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public boolean isCurrentRecord() {
        return this.endDate == null;
    }
}
