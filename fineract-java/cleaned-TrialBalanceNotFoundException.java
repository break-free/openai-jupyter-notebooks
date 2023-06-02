
package org.apache.fineract.accounting.trialbalance.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TrialBalanceNotFoundException extends AbstractPlatformResourceNotFoundException {
    public TrialBalanceNotFoundException(final Long officeId, final Long accountId) {
        super("error.msg.trial.balance.invalid",
                "Trial balance with officeId " + officeId + "and accountId" + accountId + " does not exist");
    }
}
