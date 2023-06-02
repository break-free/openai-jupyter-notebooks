
package org.apache.fineract.portfolio.savings;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.portfolio.savings.domain.FixedDepositAccount;
import org.apache.fineract.portfolio.savings.domain.RecurringDepositAccount;
public enum DepositAccountOnClosureType {
    INVALID(0, "depositAccountClosureType.invalid"), 
    WITHDRAW_DEPOSIT(100, "depositAccountClosureType.withdrawDeposit"), 
    TRANSFER_TO_SAVINGS(200, "depositAccountClosureType.transferToSavings"), 
    REINVEST_PRINCIPAL_AND_INTEREST(300, "depositAccountClosureType.reinvestPrincipalAndInterest"), REINVEST_PRINCIPAL_ONLY(400,
            "depositAccountClosureType.reinvestPrincipalOnly"); 
    private final Integer value;
    private final String code;
    DepositAccountOnClosureType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static DepositAccountOnClosureType fromInt(final Integer closureTypeValue) {
        if (closureTypeValue == null) {
            return DepositAccountOnClosureType.INVALID;
        }
        DepositAccountOnClosureType accountOnClosureType = DepositAccountOnClosureType.INVALID;
        switch (closureTypeValue) {
            case 100:
                accountOnClosureType = DepositAccountOnClosureType.WITHDRAW_DEPOSIT;
            break;
            case 200:
                accountOnClosureType = DepositAccountOnClosureType.TRANSFER_TO_SAVINGS;
            break;
            case 300:
                accountOnClosureType = DepositAccountOnClosureType.REINVEST_PRINCIPAL_AND_INTEREST;
            break;
            case 400:
                accountOnClosureType = DepositAccountOnClosureType.REINVEST_PRINCIPAL_ONLY;
            break;
        }
        return accountOnClosureType;
    }
    public boolean isWithdarwDeposit() {
        return this.value.equals(DepositAccountOnClosureType.WITHDRAW_DEPOSIT.getValue());
    }
    public boolean isTransferToSavings() {
        return this.value.equals(DepositAccountOnClosureType.TRANSFER_TO_SAVINGS.getValue());
    }
    public boolean isReinvest() {
        return this.value.equals(DepositAccountOnClosureType.REINVEST_PRINCIPAL_AND_INTEREST.getValue())
                || this.value.equals(DepositAccountOnClosureType.REINVEST_PRINCIPAL_ONLY.getValue());
    }
    public boolean isReinvestPrincipal() {
        return this.value.equals(DepositAccountOnClosureType.REINVEST_PRINCIPAL_ONLY.getValue());
    }
    public boolean isReinvestPrincipalAndInterest() {
        return this.value.equals(DepositAccountOnClosureType.REINVEST_PRINCIPAL_AND_INTEREST.getValue());
    }
    public boolean isInvalid() {
        return this.value.equals(DepositAccountOnClosureType.INVALID.getValue());
    }
    public static Object[] integerValues() {
        final List<Integer> values = new ArrayList<>();
        for (final DepositAccountOnClosureType enumType : values()) {
            if (!enumType.isInvalid()) {
                values.add(enumType.getValue());
            }
        }
        return values.toArray();
    }
}
