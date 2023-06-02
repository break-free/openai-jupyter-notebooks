
package org.apache.fineract.portfolio.savings;
import java.util.ArrayList;
import java.util.List;
public enum SavingsPostingInterestPeriodType {
    INVALID(0, "savingsPostingInterestPeriodType.invalid"), 
    DAILY(1, "savingsPostingInterestPeriodType.daily"), 
    MONTHLY(4, "savingsPostingInterestPeriodType.monthly"), 
    QUATERLY(5, "savingsPostingInterestPeriodType.quarterly"), 
    BIANNUAL(6, "savingsPostingInterestPeriodType.biannual"), ANNUAL(7, "savingsPostingInterestPeriodType.annual");
    private final Integer value;
    private final String code;
    SavingsPostingInterestPeriodType(final Integer value, final String code) {
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
        for (final SavingsPostingInterestPeriodType enumType : values()) {
            if (enumType.getValue() > 0) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
    public static SavingsPostingInterestPeriodType fromInt(final Integer type) {
        SavingsPostingInterestPeriodType repaymentFrequencyType = SavingsPostingInterestPeriodType.INVALID;
        if (type != null) {
            switch (type) {
                case 1:
                    repaymentFrequencyType = SavingsPostingInterestPeriodType.DAILY;
                break;
                case 4:
                    repaymentFrequencyType = SavingsPostingInterestPeriodType.MONTHLY;
                break;
                case 5:
                    repaymentFrequencyType = SavingsPostingInterestPeriodType.QUATERLY;
                break;
                case 6:
                    repaymentFrequencyType = SavingsPostingInterestPeriodType.BIANNUAL;
                break;
                case 7:
                    repaymentFrequencyType = SavingsPostingInterestPeriodType.ANNUAL;
                break;
            }
        }
        return repaymentFrequencyType;
    }
}
