
package org.apache.fineract.accounting.financialactivityaccount.exception;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class FinancialActivityAccountInvalidException extends AbstractPlatformDomainRuleException {
    private static final String errorCode = "error.msg.financialActivityAccount.invalid";
    public FinancialActivityAccountInvalidException(final FinancialActivity financialActivity, final GLAccount glAccount) {
        super(errorCode,
                "Financial Activity '" + financialActivity.getCode() + "' with Id :" + financialActivity.getValue()
                        + "' can only be associated with a Ledger Account of Type " + financialActivity.getMappedGLAccountType().getCode()
                        + " the provided Ledger Account '" + glAccount.getName() + "(" + glAccount.getGlCode()
                        + ")'  does not of the required type",
                financialActivity.getCode(), financialActivity.getValue(), financialActivity.getMappedGLAccountType().getCode(),
                glAccount.getName(), glAccount.getGlCode());
    }
    public static String getErrorcode() {
        return errorCode;
    }
}
