
package org.apache.fineract.accounting.rule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class AccountingRuleNotFoundException extends AbstractPlatformResourceNotFoundException {
    public AccountingRuleNotFoundException(final Long id) {
        super("error.msg.accounting.rule.id.invalid", "Accounting Rule with identifier " + id + " does not exist", id);
    }
    public AccountingRuleNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super("error.msg.accounting.rule.id.invalid", "Accounting Rule with identifier " + id + " does not exist", id, e);
    }
}
