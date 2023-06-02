
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidLendingStrategy extends AbstractPlatformDomainRuleException {
    public InvalidLendingStrategy(final Integer strategyId) {
        super("error.msg.unsupported.lending.strategy", "Stratagy code [" + strategyId + "] passed is not valid.");
    }
}
