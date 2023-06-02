
package org.apache.fineract.portfolio.rate.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.fineract.portfolio.rate.exception.RateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RateRepositoryWrapper {
    private final RateRepository repository;
    @Autowired
    public RateRepositoryWrapper(final RateRepository repository) {
        this.repository = repository;
    }
    public Rate findOneWithNotFoundDetection(final Long rateId) {
        final Rate rate = this.repository.findById(rateId).orElseThrow(() -> new RateNotFoundException(rateId));
        return rate;
    }
    public List<Rate> findMultipleWithNotFoundDetection(final List<Long> rateIds) {
        List<Rate> rates = new ArrayList<>();
        if (rateIds != null && !rateIds.isEmpty()) {
            final List<Rate> foundRates = this.repository.findAllById(rateIds);
            for (Long rateId : rateIds) {
                Boolean found = false;
                for (Rate foundRate : foundRates) {
                    if (Objects.equals(foundRate.getId(), rateId)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new RateNotFoundException(rateId);
                }
            }
            rates.addAll(foundRates);
        }
        return rates;
    }
}
