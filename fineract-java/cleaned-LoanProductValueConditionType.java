
package org.apache.fineract.portfolio.loanproduct.domain;
public enum LoanProductValueConditionType {
    INVALID(0, "LoanProductParamType.invalid"), 
    EQUAL(2, "LoanProductValueConditionType.equal"), 
    GREATERTHAN(3, "LoanProductValueConditionType.greaterThan"); 
    private final Integer value;
    private final String code;
    LoanProductValueConditionType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static LoanProductValueConditionType fromInt(final Integer type) {
        LoanProductValueConditionType loanProductParamType = LoanProductValueConditionType.INVALID;
        if (type != null) {
            switch (type) {
                case 2:
                    loanProductParamType = EQUAL;
                break;
                case 3:
                    loanProductParamType = GREATERTHAN;
                break;
                default:
                    loanProductParamType = INVALID;
                break;
            }
        }
        return loanProductParamType;
    }
    public boolean isValueConditionTypeEqual() {
        return LoanProductValueConditionType.EQUAL.getValue().equals(this.value);
    }
    public boolean isValueConditionTypeGreterThan() {
        return LoanProductValueConditionType.GREATERTHAN.getValue().equals(this.value);
    }
}
