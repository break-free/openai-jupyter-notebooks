
package org.apache.fineract.accounting.glaccount.domain;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public enum GLAccountType {
    ASSET(1, "accountType.asset"), LIABILITY(2, "accountType.liability"), EQUITY(3, "accountType.equity"), INCOME(4,
            "accountType.income"), EXPENSE(5, "accountType.expense");
    private final Integer value;
    private final String code;
    GLAccountType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    private static final Map<Integer, GLAccountType> intToEnumMap = new HashMap<>();
    private static int minValue;
    private static int maxValue;
    static {
        int i = 0;
        for (final GLAccountType type : GLAccountType.values()) {
            if (i == 0) {
                minValue = type.value;
            }
            intToEnumMap.put(type.value, type);
            if (minValue >= type.value) {
                minValue = type.value;
            }
            if (maxValue < type.value) {
                maxValue = type.value;
            }
            i = i + 1;
        }
    }
    public static EnumOptionData fromString(String accountType) {
        Long accountTypeId = null;
        if (accountType != null && accountType.equalsIgnoreCase(ASSET.toString())) {
            accountTypeId = 1L;
            return new EnumOptionData(accountTypeId, null, null);
        } else if (accountType != null && accountType.equalsIgnoreCase(LIABILITY.toString())) {
            accountTypeId = 2L;
            return new EnumOptionData(accountTypeId, null, null);
        } else if (accountType != null && accountType.equalsIgnoreCase(EQUITY.toString())) {
            accountTypeId = 3L;
            return new EnumOptionData(accountTypeId, null, null);
        } else if (accountType != null && accountType.equalsIgnoreCase(INCOME.toString())) {
            accountTypeId = 4L;
            return new EnumOptionData(accountTypeId, null, null);
        } else if (accountType != null && accountType.equalsIgnoreCase(EXPENSE.toString())) {
            accountTypeId = 5L;
            return new EnumOptionData(accountTypeId, null, null);
        } else {
            return null;
        }
    }
    public static GLAccountType fromInt(final int i) {
        final GLAccountType type = intToEnumMap.get(Integer.valueOf(i));
        return type;
    }
    public static int getMinValue() {
        return minValue;
    }
    public static int getMaxValue() {
        return maxValue;
    }
    @Override
    public String toString() {
        return name().toString();
    }
    public boolean isAssetType() {
        return this.value.equals(GLAccountType.ASSET.getValue());
    }
    public boolean isLiabilityType() {
        return this.value.equals(GLAccountType.LIABILITY.getValue());
    }
    public boolean isEquityType() {
        return this.value.equals(GLAccountType.EQUITY.getValue());
    }
    public boolean isIncomeType() {
        return this.value.equals(GLAccountType.INCOME.getValue());
    }
    public boolean isExpenseType() {
        return this.value.equals(GLAccountType.EXPENSE.getValue());
    }
}
