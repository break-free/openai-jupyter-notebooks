
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
@SuppressWarnings("serial")
public class EqualAmortizationUnsupportedFeatureException extends AbstractPlatformDomainRuleException {
    public EqualAmortizationUnsupportedFeatureException(String code, String property) {
        super("error.msg.equal.amortization.does.not.support." + code, "Equal Amortization does not support " + property, property);
    }
}
