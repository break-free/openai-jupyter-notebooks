
package org.apache.fineract.accounting.rule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class AccountingRuleDuplicateException extends AbstractPlatformDomainRuleException {
    public AccountingRuleDuplicateException(final String name) {
        super("error.msg.accounting.rule.duplicate", "An accounting rule with the name " + name + " already exists" + name);
    }
    public AccountingRuleDuplicateException() {
        super("error.msg.accounting.rule.tag.duplicate", "The accounting rule already have the tags which you defined");
    }
}
