
package org.apache.fineract.organisation.workingdays.service;
import org.apache.fineract.organisation.workingdays.data.WorkingDaysData;
public interface WorkingDaysReadPlatformService {
    WorkingDaysData retrieve();
    WorkingDaysData repaymentRescheduleType();
}
