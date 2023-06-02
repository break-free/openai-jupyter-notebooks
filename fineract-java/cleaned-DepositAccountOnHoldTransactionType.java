
package org.apache.fineract.portfolio.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public enum DepositAccountOnHoldTransactionType {
    INVALID(0, "deposutAccountOnHoldTransactionType.invalid"), 
    HOLD(1, "deposutAccountOnHoldTransactionType.hold"), 
    RELEASE(2, "deposutAccountOnHoldTransactionType.release");
    private final Integer value;
    private final String code;
    DepositAccountOnHoldTransactionType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static DepositAccountOnHoldTransactionType fromInt(final Integer transactionType) {
        if (transactionType == null) {
            return DepositAccountOnHoldTransactionType.INVALID;
        }
        DepositAccountOnHoldTransactionType savingsAccountTransactionType = DepositAccountOnHoldTransactionType.INVALID;
        switch (transactionType) {
            case 1:
                savingsAccountTransactionType = DepositAccountOnHoldTransactionType.HOLD;
            break;
            case 2:
                savingsAccountTransactionType = DepositAccountOnHoldTransactionType.RELEASE;
            break;
        }
        return savingsAccountTransactionType;
    }
    public boolean isHold() {
        return this.value.equals(DepositAccountOnHoldTransactionType.HOLD.getValue());
    }
    public boolean isRelease() {
        return this.value.equals(DepositAccountOnHoldTransactionType.RELEASE.getValue());
    }
}
