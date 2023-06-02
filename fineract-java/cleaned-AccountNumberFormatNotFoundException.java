
package org.apache.fineract.infrastructure.accountnumberformat.exception;
import org.apache.fineract.infrastructure.accountnumberformat.service.AccountNumberFormatConstants;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class AccountNumberFormatNotFoundException extends AbstractPlatformResourceNotFoundException {
    public AccountNumberFormatNotFoundException(final Long id) {
        super(AccountNumberFormatConstants.EXCEPTION_ACCOUNT_NUMBER_FORMAT_NOT_FOUND,
                "AccountNumber format with identifier " + id + " does not exist", id);
    }
    public AccountNumberFormatNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super(AccountNumberFormatConstants.EXCEPTION_ACCOUNT_NUMBER_FORMAT_NOT_FOUND,
                "AccountNumber format with identifier " + id + " does not exist", id, e);
    }
}
