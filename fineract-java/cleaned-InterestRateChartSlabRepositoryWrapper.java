
package org.apache.fineract.portfolio.interestratechart.domain;
import org.apache.fineract.portfolio.interestratechart.exception.InterestRateChartSlabNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InterestRateChartSlabRepositoryWrapper {
    private final InterestRateChartSlabRepository repository;
    @Autowired
    public InterestRateChartSlabRepositoryWrapper(final InterestRateChartSlabRepository repository) {
        this.repository = repository;
    }
    public InterestRateChartSlab findOneWithNotFoundDetection(final Long chartSlabId) {
        return this.repository.findById(chartSlabId).orElseThrow(() -> new InterestRateChartSlabNotFoundException(chartSlabId));
    }
    public void save(final InterestRateChartSlab chartSlab) {
        this.repository.save(chartSlab);
    }
    public void delete(final InterestRateChartSlab chartSlab) {
        this.repository.delete(chartSlab);
    }
    public void saveAndFlush(final InterestRateChartSlab chartSlab) {
        this.repository.saveAndFlush(chartSlab);
    }
}
