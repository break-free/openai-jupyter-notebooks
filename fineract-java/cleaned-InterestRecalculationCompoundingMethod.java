
package org.apache.fineract.portfolio.loanproduct.domain;
import java.util.HashMap;
import java.util.Map;
public enum InterestRecalculationCompoundingMethod {
    NONE(0, "interestRecalculationCompoundingMethod.none"), 
    INTEREST(1, "interestRecalculationCompoundingMethod.interest"), 
    FEE(2, "interestRecalculationCompoundingMethod.fee"), 
    INTEREST_AND_FEE(3, "interestRecalculationCompoundingMethod.interest.and.fee");
    private final Integer value;
    private final String code;
    private static final Map<Integer, InterestRecalculationCompoundingMethod> intToEnumMap = new HashMap<>();
    static {
        for (final InterestRecalculationCompoundingMethod type : InterestRecalculationCompoundingMethod.values()) {
            intToEnumMap.put(type.value, type);
        }
    }
    public static InterestRecalculationCompoundingMethod fromInt(final Integer ruleTypeValue) {
        final InterestRecalculationCompoundingMethod type = intToEnumMap.get(ruleTypeValue);
        return type;
    }
    InterestRecalculationCompoundingMethod(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isFeeCompoundingEnabled() {
        return this.getValue().equals(InterestRecalculationCompoundingMethod.FEE.getValue())
                || this.getValue().equals(InterestRecalculationCompoundingMethod.INTEREST_AND_FEE.getValue());
    }
    public boolean isCompoundingEnabled() {
        return !this.getValue().equals(InterestRecalculationCompoundingMethod.NONE.getValue());
    }
    public boolean isInterestCompoundingEnabled() {
        return this.getValue().equals(InterestRecalculationCompoundingMethod.INTEREST.getValue())
                || this.getValue().equals(InterestRecalculationCompoundingMethod.INTEREST_AND_FEE.getValue());
    }
}
