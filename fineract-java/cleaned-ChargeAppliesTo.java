
package org.apache.fineract.portfolio.charge.domain;
public enum ChargeAppliesTo {
    INVALID(0, "chargeAppliesTo.invalid"), 
    LOAN(1, "chargeAppliesTo.loan"), 
    SAVINGS(2, "chargeAppliesTo.savings"), 
    CLIENT(3, "chargeAppliesTo.client"), SHARES(4, "chargeAppliesTo.shares");
    private final Integer value;
    private final String code;
    ChargeAppliesTo(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static ChargeAppliesTo fromInt(final Integer chargeAppliesTo) {
        ChargeAppliesTo chargeAppliesToType = ChargeAppliesTo.INVALID;
        if (chargeAppliesTo != null) {
            switch (chargeAppliesTo) {
                case 1:
                    chargeAppliesToType = LOAN;
                break;
                case 2:
                    chargeAppliesToType = SAVINGS;
                break;
                case 3:
                    chargeAppliesToType = CLIENT;
                break;
                case 4:
                    chargeAppliesToType = SHARES;
                break;
                default:
                    chargeAppliesToType = INVALID;
                break;
            }
        }
        return chargeAppliesToType;
    }
    public boolean isLoanCharge() {
        return this.value.equals(ChargeAppliesTo.LOAN.getValue());
    }
    public boolean isSavingsCharge() {
        return this.value.equals(ChargeAppliesTo.SAVINGS.getValue());
    }
    public boolean isClientCharge() {
        return this.value.equals(ChargeAppliesTo.CLIENT.getValue());
    }
    public boolean isSharesCharge() {
        return this.value.equals(SHARES.getValue());
    }
    public static Object[] validValues() {
        return new Object[] { ChargeAppliesTo.LOAN.getValue(), ChargeAppliesTo.SAVINGS.getValue(), ChargeAppliesTo.CLIENT.getValue(),
                ChargeAppliesTo.SHARES.getValue() };
    }
}
