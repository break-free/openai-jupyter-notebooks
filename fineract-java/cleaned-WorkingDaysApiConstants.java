
package org.apache.fineract.organisation.workingdays.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class WorkingDaysApiConstants {
    private WorkingDaysApiConstants() {
    }
    public static final String WORKING_DAYS_RESOURCE_NAME = "workingdays";
    public static final String recurrence = "recurrence";
    public static final String repayment_rescheduling_enum = "repaymentRescheduleType";
    public static final String idParamName = "id";
    public static final String rescheduleRepaymentTemplate = "rescheduleRepaymentTemplate";
    public static final String localeParamName = "locale";
    public static final String extendTermForDailyRepayments = "extendTermForDailyRepayments";
    public static final String extendTermForRepaymentsOnHolidays = "extendTermForRepaymentsOnHolidays";
    static final Set<String> WORKING_DAYS_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, recurrence,
            repayment_rescheduling_enum, extendTermForDailyRepayments, extendTermForRepaymentsOnHolidays));
    static final Set<String> WORKING_DAYS_TEMPLATE_PARAMETERS = new HashSet<>(Arrays.asList(rescheduleRepaymentTemplate));
}
