
package org.apache.fineract.portfolio.savings;
import java.util.ArrayList;
import java.util.List;
public enum SavingsInterestCalculationDaysInYearType {
    INVALID(0, "savingsInterestCalculationDaysInYearType.invalid"), 
    DAYS_360(360, "savingsInterestCalculationDaysInYearType.days360"), 
    DAYS_365(365, "savingsInterestCalculationDaysInYearType.days365");
    private final Integer value;
    private final String code;
    SavingsInterestCalculationDaysInYearType(final Integer value, final String code) {
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
        for (final SavingsInterestCalculationDaysInYearType enumType : values()) {
            if (enumType.getValue() > 0) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
    public static SavingsInterestCalculationDaysInYearType fromInt(final Integer type) {
        SavingsInterestCalculationDaysInYearType repaymentFrequencyType = SavingsInterestCalculationDaysInYearType.INVALID;
        if (type != null) {
            switch (type) {
                case 360:
                    repaymentFrequencyType = SavingsInterestCalculationDaysInYearType.DAYS_360;
                break;
                case 365:
                    repaymentFrequencyType = SavingsInterestCalculationDaysInYearType.DAYS_365;
                break;
            }
        }
        return repaymentFrequencyType;
    }
}
