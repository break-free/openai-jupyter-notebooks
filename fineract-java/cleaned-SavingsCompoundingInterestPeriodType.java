
package org.apache.fineract.portfolio.savings;
import java.util.ArrayList;
import java.util.List;
public enum SavingsCompoundingInterestPeriodType {
    INVALID(0, "savingsCompoundingInterestPeriodType.invalid"), 
    DAILY(1, "savingsCompoundingInterestPeriodType.daily"), 
    MONTHLY(4, "savingsCompoundingInterestPeriodType.monthly"),
    QUATERLY(5, "savingsCompoundingInterestPeriodType.quarterly"), 
    BI_ANNUAL(6, "savingsCompoundingInterestPeriodType.biannual"), 
    ANNUAL(7, "savingsCompoundingInterestPeriodType.annual"); 
    private final Integer value;
    private final String code;
    SavingsCompoundingInterestPeriodType(final Integer value, final String code) {
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
        for (final SavingsCompoundingInterestPeriodType enumType : values()) {
            if (enumType.getValue() > 0) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
    public static SavingsCompoundingInterestPeriodType fromInt(final Integer type) {
        SavingsCompoundingInterestPeriodType repaymentFrequencyType = SavingsCompoundingInterestPeriodType.INVALID;
        if (type != null) {
            switch (type) {
                case 1:
                    repaymentFrequencyType = SavingsCompoundingInterestPeriodType.DAILY;
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                    repaymentFrequencyType = SavingsCompoundingInterestPeriodType.MONTHLY;
                break;
                case 5:
                    repaymentFrequencyType = SavingsCompoundingInterestPeriodType.QUATERLY;
                break;
                case 6:
                    repaymentFrequencyType = SavingsCompoundingInterestPeriodType.BI_ANNUAL;
                break;
                case 7:
                    repaymentFrequencyType = SavingsCompoundingInterestPeriodType.ANNUAL;
                break;
                case 8:
                break;
            }
        }
        return repaymentFrequencyType;
    }
}
