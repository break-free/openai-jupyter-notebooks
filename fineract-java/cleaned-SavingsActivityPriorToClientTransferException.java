
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsActivityPriorToClientTransferException extends AbstractPlatformDomainRuleException {
    public SavingsActivityPriorToClientTransferException(final String action, final Object... defaultUserMessageArgs) {
        super("error.msg.savings." + action + "." + "not.permitted.before.client.transfer.date",
                "Transactions on savings account prior to the customer joining date are not permitted", defaultUserMessageArgs);
    }
}
