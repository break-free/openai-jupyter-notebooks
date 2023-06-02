
package org.apache.fineract.portfolio.loanproduct.domain;
import java.util.HashMap;
import java.util.Map;
public enum LoanPreClosureInterestCalculationStrategy {
    NONE(0, "loanPreClosureInterestCalculationStrategy.none"), 
    TILL_PRE_CLOSURE_DATE(1, "loanPreClosureInterestCalculationStrategy.tillPreClosureDate"), 
    TILL_REST_FREQUENCY_DATE(2, "loanPreClosureInterestCalculationStrategy.tillRestFrequencyDate");
    private final Integer value;
    private final String code;
    private static final Map<Integer, LoanPreClosureInterestCalculationStrategy> intToEnumMap = new HashMap<>();
    private static int minValue;
    private static int maxValue;
    static {
        int i = 0;
        for (final LoanPreClosureInterestCalculationStrategy type : LoanPreClosureInterestCalculationStrategy.values()) {
            if (i == 0) {
                minValue = type.value;
            }
            intToEnumMap.put(type.value, type);
            if (minValue >= type.value) {
                minValue = type.value;
            }
            if (maxValue < type.value) {
                maxValue = type.value;
            }
            i = i + 1;
        }
    }
    public static LoanPreClosureInterestCalculationStrategy fromInt(final Integer ruleTypeValue) {
        final LoanPreClosureInterestCalculationStrategy type = intToEnumMap.get(ruleTypeValue);
        return type;
    }
    LoanPreClosureInterestCalculationStrategy(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static int getMinValue() {
        return minValue;
    }
    public static int getMaxValue() {
        return maxValue;
    }
    public boolean calculateTillRestFrequencyEnabled() {
        return this.getValue().equals(LoanPreClosureInterestCalculationStrategy.TILL_REST_FREQUENCY_DATE.getValue());
    }
    public boolean calculateTillPreClosureDateEnabled() {
        return this.getValue().equals(LoanPreClosureInterestCalculationStrategy.TILL_PRE_CLOSURE_DATE.getValue());
    }
}
