
package org.apache.fineract.accounting.financialactivityaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class FinancialActivityAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FinancialActivityAccountNotFoundException(final Long id) {
        super("error.msg.financialActivityAccount.not.found", "Financial Activity account with Id " + id + " does not exist", id);
    }
    public FinancialActivityAccountNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super("error.msg.financialActivityAccount.not.found", "Financial Activity account with Id " + id + " does not exist", id, e);
    }
    public FinancialActivityAccountNotFoundException(final Integer financialActivityType) {
        super("error.msg.financialActivityAccount.not.found",
                "Financial Activity account with for the financial Activity with Id " + financialActivityType + " does not exist",
                financialActivityType);
    }
}
