
package org.apache.fineract.portfolio.self.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidAccountInformationException extends AbstractPlatformDomainRuleException {
    public InvalidAccountInformationException(final String officeName, final String accountNumber, final String accountType) {
        super("error.msg.beneficiary.invalid.account.details.with.officeName." + officeName + ".accountNumber." + accountNumber
                + ".accountType." + accountType, "Invalid Office Name, Account Number, Account Type combination");
    }
}
