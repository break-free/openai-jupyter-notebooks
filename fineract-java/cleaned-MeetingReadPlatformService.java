
package org.apache.fineract.portfolio.meeting.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.meeting.data.MeetingData;
public interface MeetingReadPlatformService {
    MeetingData retrieveMeeting(Long meetingId, Long entityId, Integer entityTypeId);
    Collection<MeetingData> retrieveMeetingsByEntity(Long entityId, Integer entityTypeId, Integer limit);
    Collection<MeetingData> retrieveMeetingsByEntityByCalendarType(Long entityId, Integer entityTypeId, List<Integer> calendarTypeOptions);
    MeetingData retrieveLastMeeting(Long calendarInstanceId);
}
