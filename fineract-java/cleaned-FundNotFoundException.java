
package org.apache.fineract.portfolio.fund.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class FundNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FundNotFoundException(final Long id) {
        super("error.msg.fund.id.invalid", "Fund with identifier " + id + " does not exist", id);
    }
    public FundNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.fund.id.invalid", "Fund with identifier " + id + " does not exist", id, e);
    }
}
