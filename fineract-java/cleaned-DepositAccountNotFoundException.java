
package org.apache.fineract.portfolio.savings.exception;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.springframework.dao.EmptyResultDataAccessException;
public class DepositAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DepositAccountNotFoundException(final DepositAccountType accountType, final Long id) {
        super("error.msg." + accountType.getCode().toLowerCase() + ".id.invalid",
                StringUtils.capitalize(accountType.toString().toLowerCase()) + " account with identifier " + id + " does not exist", id);
    }
    public DepositAccountNotFoundException(DepositAccountType accountType, Long id, EmptyResultDataAccessException e) {
        super("error.msg." + accountType.getCode().toLowerCase() + ".id.invalid",
                StringUtils.capitalize(accountType.toString().toLowerCase()) + " account with identifier " + id + " does not exist", id, e);
    }
}
