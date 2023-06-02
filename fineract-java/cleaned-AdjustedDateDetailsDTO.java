
package org.apache.fineract.organisation.workingdays.data;
import java.time.LocalDate;
public class AdjustedDateDetailsDTO {
    LocalDate changedScheduleDate;
    LocalDate changedActualRepaymentDate;
    LocalDate nextRepaymentPeriodDueDate;
    public AdjustedDateDetailsDTO(final LocalDate changedScheduleDate, final LocalDate changedActualRepaymentDate) {
        this.changedScheduleDate = changedScheduleDate;
        this.changedActualRepaymentDate = changedActualRepaymentDate;
    }
    public AdjustedDateDetailsDTO(final LocalDate changedScheduleDate, final LocalDate changedActualRepaymentDate,
            final LocalDate nextRepaymentPeriodDueDate) {
        this.changedScheduleDate = changedScheduleDate;
        this.changedActualRepaymentDate = changedActualRepaymentDate;
        this.nextRepaymentPeriodDueDate = nextRepaymentPeriodDueDate;
    }
    public LocalDate getChangedScheduleDate() {
        return this.changedScheduleDate;
    }
    public LocalDate getChangedActualRepaymentDate() {
        return this.changedActualRepaymentDate;
    }
    public void setChangedScheduleDate(final LocalDate changedScheduleDate) {
        this.changedScheduleDate = changedScheduleDate;
    }
    public void setChangedActualRepaymentDate(final LocalDate changedActualRepaymentDate) {
        this.changedActualRepaymentDate = changedActualRepaymentDate;
    }
    public LocalDate getNextRepaymentPeriodDueDate() {
        return this.nextRepaymentPeriodDueDate;
    }
    public void setNextRepaymentPeriodDueDate(final LocalDate nextRepaymentPeriodDueDate) {
        this.nextRepaymentPeriodDueDate = nextRepaymentPeriodDueDate;
    }
}
