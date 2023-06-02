
package org.apache.fineract.portfolio.loanaccount.guarantor.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidGuarantorException extends AbstractPlatformDomainRuleException {
    public InvalidGuarantorException(final Long clientId, final Long loanId) {
        super("error.msg.invalid.guarantor",
                "Tried to set Client with id " + clientId + " as a guarantor to his/her own loan with loan identifier =" + loanId, clientId,
                loanId);
    }
    public InvalidGuarantorException(final Long clientId, final Long loanId, final String errorcode) {
        super("error.msg." + errorcode,
                "Tried to set Client with id " + clientId + " as a guarantor to his/her own loan with loan identifier =" + loanId, clientId,
                loanId);
    }
}
