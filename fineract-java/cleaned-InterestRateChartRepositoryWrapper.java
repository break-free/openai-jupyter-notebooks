
package org.apache.fineract.portfolio.interestratechart.domain;
import org.apache.fineract.portfolio.interestratechart.exception.InterestRateChartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InterestRateChartRepositoryWrapper {
    private final InterestRateChartRepository repository;
    @Autowired
    public InterestRateChartRepositoryWrapper(final InterestRateChartRepository repository) {
        this.repository = repository;
    }
    public InterestRateChart findOneWithNotFoundDetection(final Long intrestRateChartId) {
        return this.repository.findById(intrestRateChartId).orElseThrow(() -> new InterestRateChartNotFoundException(intrestRateChartId));
    }
    public void save(final InterestRateChart interestRateChart) {
        this.repository.save(interestRateChart);
    }
    public void delete(final InterestRateChart interestRateChart) {
        this.repository.delete(interestRateChart);
    }
    public void saveAndFlush(final InterestRateChart interestRateChart) {
        this.repository.saveAndFlush(interestRateChart);
    }
}
