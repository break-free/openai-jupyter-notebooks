
package org.apache.fineract.accounting.journalentry.api;
import java.util.HashSet;
import java.util.Set;
public enum JournalEntryJsonInputParams {
    OFFICE_ID("officeId"), TRANSACTION_DATE("transactionDate"), COMMENTS("comments"), CREDITS("credits"), DEBITS("debits"), LOCALE(
            "locale"), DATE_FORMAT("dateFormat"), REFERENCE_NUMBER("referenceNumber"), USE_ACCOUNTING_RULE(
                    "useAccountingRule"), ACCOUNTING_RULE("accountingRule"), AMOUNT("amount"), CURRENCY_CODE(
                            "currencyCode"), PAYMENT_TYPE_ID("paymentTypeId"), ACCOUNT_NUMBER("accountNumber"), CHECK_NUMBER(
                                    "checkNumber"), ROUTING_CODE("routingCode"), RECEIPT_NUMBER("receiptNumber"), BANK_NUMBER("bankNumber");
    private final String value;
    JournalEntryJsonInputParams(final String value) {
        this.value = value;
    }
    private static final Set<String> values = new HashSet<>();
    static {
        for (final JournalEntryJsonInputParams type : JournalEntryJsonInputParams.values()) {
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
