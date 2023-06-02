
package org.apache.fineract.portfolio.loanaccount.exception;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidAmountOfCollateralQuantity extends AbstractPlatformDomainRuleException {
    public InvalidAmountOfCollateralQuantity(final BigDecimal quantity) {
        super("error.msg.loan.collateral.failed",
                "The collateral quantity value`" + quantity.toString() + "`" + "` cannot exceed the available quantity",
                new Object[] { quantity.toString() });
    }
    public InvalidAmountOfCollateralQuantity(final String defaultUserMessage, final String entity, final Object... defaultUserMessageArgs) {
        super("error.msg.loan." + entity, defaultUserMessage, defaultUserMessageArgs);
    }
}
