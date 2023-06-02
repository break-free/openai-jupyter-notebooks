
package org.apache.fineract.accounting.rule.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class AccountingRuleInvalidDeleteException extends AbstractPlatformDomainRuleException {
    public AccountingRuleInvalidDeleteException(final Long officeId, final String officeName, final LocalDate latestclosureDate) {
        super("error.msg.glclosure.invalid.delete", "The latest closure for office with Id " + officeId + " and name " + officeName
                + " is on " + latestclosureDate.toString() + ", please delete this closure first", latestclosureDate);
    }
}
