
package org.apache.fineract.accounting.rule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class AccountingRuleDataException extends AbstractPlatformDomainRuleException {
    public AccountingRuleDataException(final String debitOrCreditAccount, final String debitOrCreditTags) {
        super("error.msg.accounting.rule." + debitOrCreditAccount + ".or." + debitOrCreditTags + ".required",
                debitOrCreditAccount + " or " + debitOrCreditTags + " required", debitOrCreditAccount, debitOrCreditTags);
    }
}
