
package org.apache.fineract.portfolio.loanaccount.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.organisation.staff.domain.Staff;
@Entity
@Table(name = "m_loan_officer_assignment_history")
public class LoanOfficerAssignmentHistory extends AbstractAuditableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @ManyToOne
    @JoinColumn(name = "loan_officer_id", nullable = true)
    private Staff loanOfficer;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    public static LoanOfficerAssignmentHistory createNew(final Loan loan, final Staff loanOfficer, final LocalDate startDate) {
        return new LoanOfficerAssignmentHistory(loan, loanOfficer, startDate, null);
    }
    protected LoanOfficerAssignmentHistory() {
    }
    private LoanOfficerAssignmentHistory(final Loan loan, final Staff loanOfficer, final LocalDate startDate, final LocalDate endDate) {
        this.loan = loan;
        this.loanOfficer = loanOfficer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void updateLoanOfficer(final Staff loanOfficer) {
        this.loanOfficer = loanOfficer;
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
        return this.endDate.isAfter(compareDate);
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public boolean isSameLoanOfficer(final Staff staff) {
        return this.loanOfficer.identifiedBy(staff);
    }
}
