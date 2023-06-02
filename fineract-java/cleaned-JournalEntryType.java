
package org.apache.fineract.accounting.journalentry.domain;
import java.util.HashMap;
import java.util.Map;
public enum JournalEntryType {
    CREDIT(1, "journalEntryType.credit"), DEBIT(2, "journalEntrytType.debit");
    private final Integer value;
    private final String code;
    JournalEntryType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    private static final Map<Integer, JournalEntryType> intToEnumMap = new HashMap<>();
    static {
        for (final JournalEntryType type : JournalEntryType.values()) {
            intToEnumMap.put(type.value, type);
        }
    }
    public static JournalEntryType fromInt(final int i) {
        final JournalEntryType type = intToEnumMap.get(Integer.valueOf(i));
        return type;
    }
    @Override
    public String toString() {
        return name().toString();
    }
    public boolean isDebitType() {
        return this.value.equals(JournalEntryType.DEBIT.getValue());
    }
    public boolean isCreditType() {
        return this.value.equals(JournalEntryType.CREDIT.getValue());
    }
}
