
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class UndoLastTrancheDisbursementException extends AbstractPlatformDomainRuleException {
    public UndoLastTrancheDisbursementException(final Object... defaultUserMessageArgs) {
        super("error.msg.cannot.undo.last.disbursal.after.repayments or waivers",
                " Cannot undo last disbursement after repayments or waivers.", defaultUserMessageArgs);
    }
}
