
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class SavingsAccountTransactionNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SavingsAccountTransactionNotFoundException(final Long savingsId, final Long transactionId) {
        super("error.msg.saving.account.trasaction.id.invalid",
                "Savings account with savings identifier " + savingsId + " and trasaction identifier " + transactionId + " does not exist",
                savingsId, transactionId);
    }
}
