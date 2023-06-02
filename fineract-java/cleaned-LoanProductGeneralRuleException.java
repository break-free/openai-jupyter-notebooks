
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanProductGeneralRuleException extends AbstractPlatformDomainRuleException {
    public LoanProductGeneralRuleException(String errorMsg, String errorMsgEnglish) {
        super("error.msg." + errorMsg, errorMsgEnglish);
    }
}
