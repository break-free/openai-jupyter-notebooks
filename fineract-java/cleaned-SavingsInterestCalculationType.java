
package org.apache.fineract.portfolio.savings;
import java.util.ArrayList;
import java.util.List;
public enum SavingsInterestCalculationType {
    INVALID(0, "savingsInterestCalculationType.invalid"), 
    DAILY_BALANCE(1, "savingsInterestCalculationType.dailybalance"), 
    AVERAGE_DAILY_BALANCE(2, "savingsInterestCalculationType.averagedailybalance");
    private final Integer value;
    private final String code;
    SavingsInterestCalculationType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static Object[] integerValues() {
        final List<Integer> values = new ArrayList<>();
        for (final SavingsInterestCalculationType enumType : values()) {
            if (enumType.getValue() > 0) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
    public static SavingsInterestCalculationType fromInt(final Integer type) {
        SavingsInterestCalculationType repaymentFrequencyType = SavingsInterestCalculationType.INVALID;
        if (type != null) {
            switch (type) {
                case 1:
                    repaymentFrequencyType = SavingsInterestCalculationType.DAILY_BALANCE;
                break;
                case 2:
                    repaymentFrequencyType = SavingsInterestCalculationType.AVERAGE_DAILY_BALANCE;
                break;
            }
        }
        return repaymentFrequencyType;
    }
}
