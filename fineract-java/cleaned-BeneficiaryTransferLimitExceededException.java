
package org.apache.fineract.portfolio.self.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class BeneficiaryTransferLimitExceededException extends AbstractPlatformDomainRuleException {
    public BeneficiaryTransferLimitExceededException() {
        super("error.msg.beneficiary.transfer.amount.limit.for.beneficiary.exceeded", "Transfer amount limit for beneficiary exceeded");
    }
}
