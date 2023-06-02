
package org.apache.fineract.interoperation.domain;
import org.apache.fineract.portfolio.savings.SavingsAccountTransactionType;
public enum InteropTransactionRole {
    PAYER, PAYEE,;
    public boolean isWithdraw() {
        return this == PAYER;
    }
    public SavingsAccountTransactionType getTransactionType() {
        return this == PAYER ? SavingsAccountTransactionType.WITHDRAWAL : SavingsAccountTransactionType.DEPOSIT;
    }
}
