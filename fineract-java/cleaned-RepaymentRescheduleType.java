
package org.apache.fineract.organisation.workingdays.domain;
import java.util.HashMap;
import java.util.Map;
public enum RepaymentRescheduleType {
    INVALID(0, "RepaymentRescheduleType.invalid"), SAME_DAY(1, "RepaymentRescheduleType.same.day"), MOVE_TO_NEXT_WORKING_DAY(2,
            "RepaymentRescheduleType.move.to.next.working.day"), MOVE_TO_NEXT_REPAYMENT_MEETING_DAY(3,
                    "RepaymentRescheduleType.move.to.next.repayment.meeting.day"), MOVE_TO_PREVIOUS_WORKING_DAY(4,
                            "RepaymentRescheduleType.move.to.previous.working.day"), MOVE_TO_NEXT_MEETING_DAY(5,
                                    "RepaymentRescheduleType.move.to.next.meeting.day");
    private final Integer value;
    private final String code;
    RepaymentRescheduleType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isMoveToNextRepaymentDay() {
        return this.value.equals(RepaymentRescheduleType.MOVE_TO_NEXT_REPAYMENT_MEETING_DAY.getValue());
    }
    private static final Map<Integer, RepaymentRescheduleType> intToEnumMap = new HashMap<>();
    private static int minValue;
    private static int maxValue;
    static {
        int i = 0;
        for (final RepaymentRescheduleType entityType : RepaymentRescheduleType.values()) {
            if (i == 0) {
                minValue = entityType.value;
            }
            intToEnumMap.put(entityType.value, entityType);
            if (minValue >= entityType.value) {
                minValue = entityType.value;
            }
            if (maxValue < entityType.value) {
                maxValue = entityType.value;
            }
            i = i + 1;
        }
    }
    public static RepaymentRescheduleType fromInt(final int i) {
        final RepaymentRescheduleType repaymentRescheduleType = intToEnumMap.get(Integer.valueOf(i));
        return repaymentRescheduleType;
    }
    public static int getMinValue() {
        return minValue;
    }
    public static int getMaxValue() {
        return maxValue;
    }
    @Override
    public String toString() {
        return name().toString();
    }
}
