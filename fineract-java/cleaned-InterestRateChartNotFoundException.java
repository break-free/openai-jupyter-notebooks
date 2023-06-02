
package org.apache.fineract.portfolio.interestratechart.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class InterestRateChartNotFoundException extends AbstractPlatformResourceNotFoundException {
    public InterestRateChartNotFoundException(final Long id) {
        super("error.msg.interest.rate.chart.id.invalid", "Interest rate chart with identifier " + id + " does not exist", id);
    }
    public InterestRateChartNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.interest.rate.chart.id.invalid", "Interest rate chart with identifier " + id + " does not exist", id, e);
    }
}
