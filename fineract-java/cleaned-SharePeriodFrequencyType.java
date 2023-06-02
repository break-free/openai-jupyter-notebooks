
package org.apache.fineract.portfolio.shareproducts;
import java.util.ArrayList;
import java.util.List;
public enum SharePeriodFrequencyType {
    DAYS(0, "sharePeriodFrequencyType.days"), 
    WEEKS(1, "sharePeriodFrequencyType.weeks"), 
    MONTHS(2, "sharePeriodFrequencyType.months"), 
    YEARS(3, "sharePeriodFrequencyType.years"), 
    INVALID(4, "sharePeriodFrequencyType.invalid");
    private final Integer value;
    private final String code;
    SharePeriodFrequencyType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static SharePeriodFrequencyType fromInt(final Integer type) {
        SharePeriodFrequencyType repaymentFrequencyType = SharePeriodFrequencyType.INVALID;
        if (type != null) {
            switch (type) {
                case 0:
                    repaymentFrequencyType = SharePeriodFrequencyType.DAYS;
                break;
                case 1:
                    repaymentFrequencyType = SharePeriodFrequencyType.WEEKS;
                break;
                case 2:
                    repaymentFrequencyType = SharePeriodFrequencyType.MONTHS;
                break;
                case 3:
                    repaymentFrequencyType = SharePeriodFrequencyType.YEARS;
                break;
            }
        }
        return repaymentFrequencyType;
    }
    public boolean isInvalid() {
        return this.value.equals(SharePeriodFrequencyType.INVALID.value);
    }
    public static Object[] integerValues() {
        final List<Integer> values = new ArrayList<>();
        for (final SharePeriodFrequencyType enumType : values()) {
            if (!enumType.isInvalid()) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
}
