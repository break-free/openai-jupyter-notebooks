
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformServiceUnavailableException;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.service.SavingsEnumerations;
public class DepositAccountTransactionNotAllowedException extends AbstractPlatformServiceUnavailableException {
    public DepositAccountTransactionNotAllowedException(final Long accountId, final String action, final DepositAccountType type) {
        super("error.msg." + type.resourceName() + ".account.trasaction." + action + ".notallowed",
                SavingsEnumerations.depositType(type).getValue() + "account " + action + " transaction not allowed with account identifier "
                        + accountId,
                accountId);
    }
}
