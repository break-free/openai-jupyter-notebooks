
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class DepositAccountInterestRateChartNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DepositAccountInterestRateChartNotFoundException(final Long id) {
        super("error.msg.deposit.account.interest.rate.chart.id.invalid",
                "Deposit Account Interest rate chart with identifier " + id + " does not exist", id);
    }
    public DepositAccountInterestRateChartNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.deposit.account.interest.rate.chart.id.invalid",
                "Deposit Account Interest rate chart with identifier " + id + " does not exist", id, e);
    }
}
