
package org.apache.fineract.portfolio.group.domain;
import java.util.HashMap;
import java.util.Map;
public enum GroupTypes {
    INVALID(0L, "lendingStrategy.invalid", "invalid"), 
    CENTER(1L, "groupTypes.center", "center"), 
    GROUP(2L, "groupTypes.group", "group"); 
    private final Long id;
    private final String code;
    private final String value;
    GroupTypes(final Long id, final String code, final String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }
    private static final Map<Long, GroupTypes> intToEnumMap = new HashMap<>();
    private static long minValue;
    private static long maxValue;
    static {
        int i = 0;
        for (final GroupTypes type : GroupTypes.values()) {
            if (i == 0) {
                minValue = type.id;
            }
            intToEnumMap.put(type.id, type);
            if (minValue >= type.id) {
                minValue = type.id;
            }
            if (maxValue < type.id) {
                maxValue = type.id;
            }
            i = i + 1;
        }
    }
    public static GroupTypes fromInt(final int i) {
        final GroupTypes type = intToEnumMap.get(Integer.toUnsignedLong(i));
        return type;
    }
    public static long getMinValue() {
        return minValue;
    }
    public static long getMaxValue() {
        return maxValue;
    }
    @Override
    public String toString() {
        return name().toString();
    }
    public Long getId() {
        return this.id;
    }
    public String getCode() {
        return this.code;
    }
    public String getValue() {
        return this.value;
    }
}
