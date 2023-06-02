
package org.apache.fineract.portfolio.loanproduct.domain;
import java.util.HashMap;
import java.util.Map;
public enum LoanRescheduleStrategyMethod {
    INVALID(0, "loanRescheduleStrategyMethod.invalid"), 
    RESCHEDULE_NEXT_REPAYMENTS(1, "loanRescheduleStrategyMethod.reschedule.next.repayments"), 
    REDUCE_NUMBER_OF_INSTALLMENTS(2, "loanRescheduleStrategyMethod.reduce.number.of.installments"), 
    REDUCE_EMI_AMOUNT(3, "loanRescheduleStrategyMethod.reduce.emi.amount");
    private final Integer value;
    private final String code;
    private static final Map<Integer, LoanRescheduleStrategyMethod> intToEnumMap = new HashMap<>();
    static {
        for (final LoanRescheduleStrategyMethod type : LoanRescheduleStrategyMethod.values()) {
            intToEnumMap.put(type.value, type);
        }
    }
    public static LoanRescheduleStrategyMethod fromInt(final Integer ruleTypeValue) {
        final LoanRescheduleStrategyMethod type = intToEnumMap.get(ruleTypeValue);
        return type;
    }
    LoanRescheduleStrategyMethod(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
}
