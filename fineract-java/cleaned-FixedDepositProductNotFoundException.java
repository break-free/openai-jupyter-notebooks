
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class FixedDepositProductNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FixedDepositProductNotFoundException(final Long id) {
        super("error.msg.fixeddepositproduct.id.invalid", "Fixed deposit product with identifier " + id + " does not exist", id);
    }
    public FixedDepositProductNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.fixeddepositproduct.id.invalid", "Fixed deposit product with identifier " + id + " does not exist", id, e);
    }
}
