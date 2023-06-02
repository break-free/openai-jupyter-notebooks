
package org.apache.fineract.portfolio.savings.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.organisation.staff.domain.Staff;
@Entity
@Table(name = "m_savings_officer_assignment_history")
public class SavingsOfficerAssignmentHistory extends AbstractAuditableCustom {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private SavingsAccount savingsAccount;
    @ManyToOne
    @JoinColumn(name = "savings_officer_id", nullable = true)
    private Staff savingsOfficer;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    public static SavingsOfficerAssignmentHistory createNew(final SavingsAccount account, final Staff savingsOfficer,
            final LocalDate assignmentDate) {
        return new SavingsOfficerAssignmentHistory(account, savingsOfficer, assignmentDate, null);
    }
    protected SavingsOfficerAssignmentHistory() {
    }
    private SavingsOfficerAssignmentHistory(final SavingsAccount account, final Staff savingsOfficer, final LocalDate startDate,
            final LocalDate endDate) {
        this.savingsAccount = account;
        this.savingsOfficer = savingsOfficer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void updateSavingsOfficer(final Staff savingsOfficer) {
        this.savingsOfficer = savingsOfficer;
    }
    public boolean isSameSavingsOfficer(final Staff staff) {
        return this.savingsOfficer.identifiedBy(staff);
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
    public boolean hasStartDateBefore(final LocalDate matchingDate) {
        return matchingDate.isBefore(getStartDate());
    }
    public boolean isCurrentRecord() {
        return this.endDate == null;
    }
    public boolean isEndDateAfter(final LocalDate compareDate) {
        return this.endDate != null && this.endDate.isAfter(compareDate);
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
}
