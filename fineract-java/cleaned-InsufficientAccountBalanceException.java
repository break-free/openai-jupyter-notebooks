
package org.apache.fineract.portfolio.savings.exception;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InsufficientAccountBalanceException extends AbstractPlatformDomainRuleException {
    public InsufficientAccountBalanceException(final String paramName, final BigDecimal accountBalance, final BigDecimal withdrawalFee,
            final BigDecimal transactionAmount) {
        super(withdrawalFee != null ? "error.msg.savingsaccount.transaction.insufficient.account.balance.withdraw"
                : "error.msg.savingsaccount.transaction.insufficient.account.balance", "Insufficient account balance.", paramName,
                accountBalance, withdrawalFee, transactionAmount);
    }
}
