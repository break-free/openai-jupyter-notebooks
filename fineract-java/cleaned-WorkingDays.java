
package org.apache.fineract.organisation.workingdays.domain;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.workingdays.api.WorkingDaysApiConstants;
@Entity
@Table(name = "m_working_days")
public class WorkingDays extends AbstractPersistableCustom {
    @Column(name = "recurrence", length = 100, nullable = true)
    private String recurrence;
    @Column(name = "repayment_rescheduling_enum", nullable = false)
    private Integer repaymentReschedulingType;
    @Column(name = "extend_term_daily_repayments", nullable = false)
    private Boolean extendTermForDailyRepayments;
    @Column(name = "extend_term_holiday_repayment", nullable = false)
    private Boolean extendTermForRepaymentsOnHolidays;
    protected WorkingDays() {
    }
    protected WorkingDays(final String recurrence, final Integer repaymentReschedulingType, final Boolean extendTermForDailyRepayments,
            final Boolean extendTermForRepaymentsOnHolidays) {
        this.recurrence = recurrence;
        this.repaymentReschedulingType = repaymentReschedulingType;
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
        this.extendTermForRepaymentsOnHolidays = extendTermForRepaymentsOnHolidays;
    }
    public String getRecurrence() {
        return this.recurrence;
    }
    public Integer getRepaymentReschedulingType() {
        return this.repaymentReschedulingType;
    }
    public void setRepaymentReschedulingType(Integer repaymentReschedulingType) {
        this.repaymentReschedulingType = repaymentReschedulingType;
    }
    public Boolean getExtendTermForDailyRepayments() {
        return this.extendTermForDailyRepayments;
    }
    public Boolean getExtendTermForRepaymentsOnHolidays() {
        return this.extendTermForRepaymentsOnHolidays;
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);
        final String recurrenceParamName = "recurrence";
        if (command.isChangeInStringParameterNamed(recurrenceParamName, this.recurrence)) {
            final String newValue = command.stringValueOfParameterNamed(recurrenceParamName);
            actualChanges.put(recurrenceParamName, newValue);
            this.recurrence = newValue;
        }
        final String repaymentRescheduleTypeParamName = "repaymentRescheduleType";
        if (command.isChangeInIntegerParameterNamed(repaymentRescheduleTypeParamName, this.repaymentReschedulingType)) {
            final Integer newValue = command.integerValueOfParameterNamed(repaymentRescheduleTypeParamName);
            actualChanges.put(repaymentRescheduleTypeParamName, WorkingDaysEnumerations.workingDaysStatusType(newValue));
            this.repaymentReschedulingType = RepaymentRescheduleType.fromInt(newValue).getValue();
        }
        if (command.isChangeInBooleanParameterNamed(WorkingDaysApiConstants.extendTermForDailyRepayments,
                this.extendTermForDailyRepayments)) {
            final Boolean newValue = command.booleanPrimitiveValueOfParameterNamed(WorkingDaysApiConstants.extendTermForDailyRepayments);
            actualChanges.put(WorkingDaysApiConstants.extendTermForDailyRepayments, newValue);
            this.extendTermForDailyRepayments = newValue;
        }
        if (command.isChangeInBooleanParameterNamed(WorkingDaysApiConstants.extendTermForRepaymentsOnHolidays,
                this.extendTermForRepaymentsOnHolidays)) {
            final Boolean newValue = command
                    .booleanPrimitiveValueOfParameterNamed(WorkingDaysApiConstants.extendTermForRepaymentsOnHolidays);
            actualChanges.put(WorkingDaysApiConstants.extendTermForRepaymentsOnHolidays, newValue);
            this.extendTermForRepaymentsOnHolidays = newValue;
        }
        return actualChanges;
    }
}
