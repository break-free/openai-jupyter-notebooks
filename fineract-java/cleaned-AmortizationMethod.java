
package org.apache.fineract.portfolio.loanproduct.domain;
public enum AmortizationMethod {
    EQUAL_PRINCIPAL(0, "amortizationType.equal.principal"), 
    EQUAL_INSTALLMENTS(1, "amortizationType.equal.installments"), 
    INVALID(2, "amortizationType.invalid");
    private final Integer value;
    private final String code;
    AmortizationMethod(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static AmortizationMethod fromInt(final Integer selectedMethod) {
        if (selectedMethod == null) {
            return null;
        }
        AmortizationMethod repaymentMethod = null;
        switch (selectedMethod) {
            case 0:
                repaymentMethod = AmortizationMethod.EQUAL_PRINCIPAL;
            break;
            case 1:
                repaymentMethod = AmortizationMethod.EQUAL_INSTALLMENTS;
            break;
            default:
                repaymentMethod = AmortizationMethod.INVALID;
            break;
        }
        return repaymentMethod;
    }
    public boolean isEqualInstallment() {
        return this.value.equals(AmortizationMethod.EQUAL_INSTALLMENTS.getValue());
    }
    public boolean isEqualPrincipal() {
        return this.value.equals(AmortizationMethod.EQUAL_PRINCIPAL.getValue());
    }
}
