
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class SavingsAccountChargeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public SavingsAccountChargeNotFoundException(final Long id) {
        super("error.msg.savings.account.charge.id.invalid", "Savings Account charge with identifier " + id + " does not exist", id);
    }
    public SavingsAccountChargeNotFoundException(final Long id, final Long savingsAccountId) {
        super("error.msg.savings.account.charge.id.invalid.for.given.savings.account",
                "Savings Account charge with identifier " + id + " does not exist for Savings Account " + savingsAccountId, id,
                savingsAccountId);
    }
    public SavingsAccountChargeNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.savings.account.charge.id.invalid", "Savings Account charge with identifier " + id + " does not exist", id, e);
    }
}
