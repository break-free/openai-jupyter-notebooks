
package org.apache.fineract.portfolio.meeting.attendance.service;
import java.util.Collection;
import org.apache.fineract.portfolio.meeting.attendance.data.ClientAttendanceData;
public interface ClientAttendanceReadPlatformService {
    Collection<ClientAttendanceData> retrieveClientAttendanceByMeetingId(Long meetingId);
}
