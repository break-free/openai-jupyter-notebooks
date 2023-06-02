
package org.apache.fineract.portfolio.loanproduct.domain;
import java.util.HashMap;
import java.util.Map;
public enum LendingStrategy {
    INDIVIDUAL_LOAN(100, "lendingStrategy.individaulLoan", "individaulLoan"), 
    GROUP_LOAN(200, "lendingStrategy.groupLoan", "groupLoan"), 
    JOINT_LIABILITY_LOAN(300, "lendingStrategy.joinLiabilityLoan", "joinLiabilityLoan"), 
    LINKED_LOAN(400, "lendingStrategy.linkedLoan", "linkedLoan"), 
    INVALID(900, "lendingStrategy.invalid", "invalid");
    private final Integer id;
    private final String code;
    private final String value;
    LendingStrategy(final Integer id, final String code, final String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }
    private static final Map<Integer, LendingStrategy> intToEnumMap = new HashMap<>();
    private static int minValue;
    private static int maxValue;
    static {
        int i = 0;
        for (final LendingStrategy type : LendingStrategy.values()) {
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
    public static LendingStrategy fromInt(final int i) {
        final LendingStrategy type = intToEnumMap.get(Integer.valueOf(i));
        return type;
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
    public Integer getId() {
        return this.id;
    }
    public String getCode() {
        return this.code;
    }
    public String getValue() {
        return this.value;
    }
}
