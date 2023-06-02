
package org.apache.fineract.portfolio.meeting.attendance.data;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class ClientAttendanceData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final Long clientId;
    @SuppressWarnings("unused")
    private final String clientName;
    @SuppressWarnings("unused")
    private final EnumOptionData attendanceType;
    public static ClientAttendanceData instance(final Long id, final Long clientId, final String clientName,
            final EnumOptionData attendanceType) {
        return new ClientAttendanceData(id, clientId, clientName, attendanceType);
    }
    private ClientAttendanceData(final Long id, final Long clientId, final String clientName, final EnumOptionData attendanceType) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.attendanceType = attendanceType;
    }
}
