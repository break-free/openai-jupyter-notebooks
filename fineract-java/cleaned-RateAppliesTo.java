
package org.apache.fineract.portfolio.rate.domain;
public enum RateAppliesTo {
    INVALID(0, "rateAppliesTo.invalid"), LOAN(1, "rateAppliesTo.loan");
    private final Integer value;
    private final String code;
    RateAppliesTo(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static RateAppliesTo fromInt(final Integer rateAppliesTo) {
        RateAppliesTo rateAppliesToType = RateAppliesTo.INVALID;
        if (rateAppliesTo != null) {
            switch (rateAppliesTo) {
                case 1:
                    rateAppliesToType = LOAN;
                break;
                default:
                    rateAppliesToType = INVALID;
                break;
            }
        }
        return rateAppliesToType;
    }
    public boolean isLoanRate() {
        return this.value.equals(RateAppliesTo.LOAN.getValue());
    }
    public static Object[] validValues() {
        return new Object[] { RateAppliesTo.LOAN.getValue() };
    }
}
