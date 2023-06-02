
package org.apache.fineract.portfolio.savings.domain;
public enum SavingsAccountSubStatusEnum {
    NONE(0, "SavingsAccountSubStatusEnum.none"), 
    INACTIVE(100, "SavingsAccountSubStatusEnum.inactive"), 
    DORMANT(200, "SavingsAccountSubStatusEnum.dormant"), ESCHEAT(300, "SavingsAccountSubStatusEnum.escheat"), BLOCK(400,
            "SavingsAccountSubStatusEnum.block"), BLOCK_CREDIT(500,
                    "SavingsAccountSubStatusEnum.blockCredit"), BLOCK_DEBIT(600, "SavingsAccountSubStatusEnum.blockDebit");
    private final Integer value;
    private final String code;
    public static SavingsAccountSubStatusEnum fromInt(final Integer type) {
        SavingsAccountSubStatusEnum enumeration = SavingsAccountSubStatusEnum.NONE;
        if (null != type) {
            switch (type) {
                case 100:
                    enumeration = SavingsAccountSubStatusEnum.INACTIVE;
                break;
                case 200:
                    enumeration = SavingsAccountSubStatusEnum.DORMANT;
                break;
                case 300:
                    enumeration = SavingsAccountSubStatusEnum.ESCHEAT;
                break;
                case 400:
                    enumeration = SavingsAccountSubStatusEnum.BLOCK;
                break;
                case 500:
                    enumeration = SavingsAccountSubStatusEnum.BLOCK_CREDIT;
                break;
                case 600:
                    enumeration = SavingsAccountSubStatusEnum.BLOCK_DEBIT;
                break;
            }
        }
        return enumeration;
    }
    SavingsAccountSubStatusEnum(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public boolean hasStateOf(final SavingsAccountSubStatusEnum state) {
        return this.value.equals(state.getValue());
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isSubStatusInactive() {
        return this.value.equals(SavingsAccountSubStatusEnum.INACTIVE.getValue());
    }
    public boolean isSubStatusDormant() {
        return this.value.equals(SavingsAccountSubStatusEnum.DORMANT.getValue());
    }
    public boolean isSubStatusNone() {
        return this.value.equals(SavingsAccountSubStatusEnum.NONE.getValue());
    }
    public boolean isSubStatusEscheat() {
        return this.value.equals(SavingsAccountSubStatusEnum.ESCHEAT.getValue());
    }
    public boolean isSubStatusAccountBlocked() {
        return this.value.equals(SavingsAccountSubStatusEnum.BLOCK.getValue());
    }
    public boolean isSubStatusCreditBlocked() {
        return this.value.equals(SavingsAccountSubStatusEnum.BLOCK_CREDIT.getValue());
    }
    public boolean isSubStatusDebitBlocked() {
        return this.value.equals(SavingsAccountSubStatusEnum.BLOCK_DEBIT.getValue());
    }
}
