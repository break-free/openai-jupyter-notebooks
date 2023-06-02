
package org.apache.fineract.portfolio.meeting.attendance;
import org.apache.fineract.portfolio.meeting.attendance.domain.ClientAttendance;
public enum AttendanceType {
    INVALID(0, "attendanceType.invalid"), 
    PRESENT(1, "attendanceType.present"), 
    ABSENT(2, "attendanceType.absent"), 
    APPROVED(3, "attendanceType.approved"), 
    LEAVE(4, "attendanceType.leave"), 
    LATE(5, "attendanceType.late");
    private final Integer value;
    private final String code;
    AttendanceType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static AttendanceType fromInt(final Integer attendanceTypeId) {
        if (attendanceTypeId == null) {
            return AttendanceType.INVALID;
        }
        AttendanceType attendanceType = AttendanceType.INVALID;
        switch (attendanceTypeId) {
            case 1:
                attendanceType = AttendanceType.PRESENT;
            break;
            case 2:
                attendanceType = AttendanceType.ABSENT;
            break;
            case 3:
                attendanceType = AttendanceType.APPROVED;
            break;
            case 4:
                attendanceType = AttendanceType.LEAVE;
            break;
            case 5:
                attendanceType = AttendanceType.LATE;
            break;
        }
        return attendanceType;
    }
}
