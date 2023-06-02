
package org.apache.fineract.portfolio.loanaccount.exception;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidAmountOfCollaterals extends AbstractPlatformDomainRuleException {
    public InvalidAmountOfCollaterals(final BigDecimal amount) {
        super("error.msg.loan.collateral.failed",
                "The total collateral value`" + amount.toString() + "`" + "` should be greater than or equal to the loan amount",
                new Object[] { amount.toString() });
    }
    public InvalidAmountOfCollaterals(final String defaultUserMessage, final String entity, final Object... defaultUserMessageArgs) {
        super("error.msg.loan." + entity, defaultUserMessage, defaultUserMessageArgs);
    }
}
