
package org.apache.fineract.accounting.rule.api;
import java.util.HashSet;
import java.util.Set;
public enum AccountingRuleJsonInputParams {
    ID("id"), OFFICE_ID("officeId"), ACCOUNT_TO_DEBIT("accountToDebit"), ACCOUNT_TO_CREDIT("accountToCredit"), NAME("name"), DESCRIPTION(
            "description"), SYSTEM_DEFINED("systemDefined"), DEBIT_ACCOUNT_TAGS("debitTags"), CREDIT_ACCOUNT_TAGS(
                    "creditTags"), ALLOW_MULTIPLE_CREDIT_ENTRIES(
                            "allowMultipleCreditEntries"), ALLOW_MULTIPLE_DEBIT_ENTRIES("allowMultipleDebitEntries");
    private final String value;
    AccountingRuleJsonInputParams(final String value) {
        this.value = value;
    }
    private static final Set<String> values = new HashSet<>();
    static {
        for (final AccountingRuleJsonInputParams type : AccountingRuleJsonInputParams.values()) {
            values.add(type.value);
        }
    }
    public static Set<String> getAllValues() {
        return values;
    }
    @Override
    public String toString() {
        return name().toString().replace("_", " ");
    }
    public String getValue() {
        return this.value;
    }
}
