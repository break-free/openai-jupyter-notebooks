
package org.apache.fineract.organisation.workingdays.data;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class WorkingDaysData {
    private final Long id;
    private final String recurrence;
    private final EnumOptionData repaymentRescheduleType;
    private final Boolean extendTermForDailyRepayments;
    private final Boolean extendTermForRepaymentsOnHolidays;
    @SuppressWarnings("unused")
    private final Collection<EnumOptionData> repaymentRescheduleOptions;
    public WorkingDaysData(Long id, String recurrence, EnumOptionData repaymentRescheduleType, Boolean extendTermForDailyRepayments,
            Boolean extendTermForRepaymentsOnHolidays) {
        this.id = id;
        this.recurrence = recurrence;
        this.repaymentRescheduleType = repaymentRescheduleType;
        this.repaymentRescheduleOptions = null;
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
        this.extendTermForRepaymentsOnHolidays = extendTermForRepaymentsOnHolidays;
    }
    public WorkingDaysData(Long id, String recurrence, EnumOptionData repaymentRescheduleType,
            Collection<EnumOptionData> repaymentRescheduleOptions, Boolean extendTermForDailyRepayments,
            Boolean extendTermForRepaymentsOnHolidays) {
        this.id = id;
        this.recurrence = recurrence;
        this.repaymentRescheduleType = repaymentRescheduleType;
        this.repaymentRescheduleOptions = repaymentRescheduleOptions;
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
        this.extendTermForRepaymentsOnHolidays = extendTermForRepaymentsOnHolidays;
    }
    public WorkingDaysData(WorkingDaysData data, Collection<EnumOptionData> repaymentRescheduleOptions) {
        this.id = data.id;
        this.recurrence = data.recurrence;
        this.repaymentRescheduleType = data.repaymentRescheduleType;
        this.repaymentRescheduleOptions = repaymentRescheduleOptions;
        this.extendTermForDailyRepayments = data.extendTermForDailyRepayments;
        this.extendTermForRepaymentsOnHolidays = data.extendTermForRepaymentsOnHolidays;
    }
}
