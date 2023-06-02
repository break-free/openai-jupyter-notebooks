
package org.apache.fineract.accounting.financialactivityaccount.api;
import java.util.HashSet;
import java.util.Set;
public enum FinancialActivityAccountsJsonInputParams {
    FINANCIAL_ACTIVITY_ID("financialActivityId"), GL_ACCOUNT_ID("glAccountId");
    private final String value;
    FinancialActivityAccountsJsonInputParams(final String value) {
        this.value = value;
    }
    private static final Set<String> values = new HashSet<>();
    static {
        for (final FinancialActivityAccountsJsonInputParams type : FinancialActivityAccountsJsonInputParams.values()) {
            values.add(type.value);
        }
    }
    public static Set<String> getAllValues() {
        return values;
    }
    @Override
    public String toString() {
        return name().toString().replaceAll("_", " ");
    }
    public String getValue() {
        return this.value;
    }
}
