
package org.apache.fineract.portfolio.meeting.attendance.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.meeting.attendance.AttendanceType;
import org.springframework.stereotype.Service;
@Service
public class AttendanceDropdownReadPlatformServiceImpl implements AttendanceDropdownReadPlatformService {
    @Override
    public List<EnumOptionData> retrieveAttendanceTypeOptions() {
        return AttendanceEnumerations.attendanceType(AttendanceType.values());
    }
}
