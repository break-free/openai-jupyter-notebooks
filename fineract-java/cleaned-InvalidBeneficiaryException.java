
package org.apache.fineract.portfolio.self.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidBeneficiaryException extends AbstractPlatformDomainRuleException {
    public InvalidBeneficiaryException(final Long beneficiaryId) {
        super("error.msg.beneficiary.invalid.beneficiary.id." + beneficiaryId, "Beneficiary ID doesn't belong to the User");
    }
}
