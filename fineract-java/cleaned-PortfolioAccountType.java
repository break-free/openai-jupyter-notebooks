
package org.apache.fineract.portfolio.account;
import java.util.ArrayList;
import java.util.List;
public enum PortfolioAccountType {
    INVALID(0, "accountType.invalid"), 
    LOAN(1, "accountType.loan"), 
    SAVINGS(2, "accountType.savings");
    private final Integer value;
    private final String code;
    PortfolioAccountType(final Integer value, final String code) {
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
        for (final PortfolioAccountType enumType : values()) {
            if (enumType.getValue() > 0) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
    public static PortfolioAccountType fromInt(final Integer type) {
        PortfolioAccountType enumType = PortfolioAccountType.INVALID;
        if (type != null) {
            switch (type) {
                case 1:
                    enumType = LOAN;
                break;
                case 2:
                    enumType = SAVINGS;
                break;
            }
        }
        return enumType;
    }
    public boolean isSavingsAccount() {
        return this.value.equals(2);
    }
    public boolean isLoanAccount() {
        return this.value.equals(1);
    }
}
