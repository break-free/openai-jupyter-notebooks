
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanDisbursalException extends AbstractPlatformDomainRuleException {
    public LoanDisbursalException(final String currentProduct, final String restrictedProduct) {
        super("error.msg.loan.disbursal.failed",
                "This loan could not be disbursed as `" + currentProduct + "` and `" + restrictedProduct + "` are not allowed to co-exist",
                new Object[] { currentProduct, restrictedProduct });
    }
    public LoanDisbursalException(final String defaultUserMessage, final String entity, final Object... defaultUserMessageArgs) {
        super("error.msg.loan." + entity, defaultUserMessage, defaultUserMessageArgs);
    }
}
