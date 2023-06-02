
package org.apache.fineract.portfolio.interestratechart.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InterestRateChartSlabNotFoundException extends AbstractPlatformResourceNotFoundException {
    public InterestRateChartSlabNotFoundException(final Long id) {
        super("error.msg.interest.rate.chart.slab.id.invalid", "Interest rate chart slab with identifier " + id + " does not exist", id);
    }
    public InterestRateChartSlabNotFoundException(final Long id, final Long chartId) {
        super("error.msg.interest.rate.chart.slab.id.invalid",
                "Interest rate chart slab with identifier " + id + " does not exist in interest chart with identifier " + chartId, id,
                chartId);
    }
}
