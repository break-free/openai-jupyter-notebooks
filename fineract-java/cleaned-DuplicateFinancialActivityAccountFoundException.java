
package org.apache.fineract.accounting.financialactivityaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DuplicateFinancialActivityAccountFoundException extends AbstractPlatformDomainRuleException {
    private static final String errorCode = "error.msg.financialActivityAccount.exists";
    public DuplicateFinancialActivityAccountFoundException(final Integer financialActivityType) {
        super(errorCode, "Mapping for activity already exists " + financialActivityType, financialActivityType);
    }
    public static String getErrorcode() {
        return errorCode;
    }
}
