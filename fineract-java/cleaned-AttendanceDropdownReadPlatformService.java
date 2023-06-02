
package org.apache.fineract.portfolio.meeting.attendance.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface AttendanceDropdownReadPlatformService {
    List<EnumOptionData> retrieveAttendanceTypeOptions();
}
