
package org.apache.fineract.portfolio.floatingrates.domain;
import org.apache.fineract.portfolio.floatingrates.exception.FloatingRateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FloatingRateRepositoryWrapper {
    private final FloatingRateRepository floatingRateRepository;
    @Autowired
    public FloatingRateRepositoryWrapper(final FloatingRateRepository floatingRateRepository) {
        this.floatingRateRepository = floatingRateRepository;
    }
    public FloatingRate retrieveBaseLendingRate() {
        return this.floatingRateRepository.retrieveBaseLendingRate();
    }
    public FloatingRate findOneWithNotFoundDetection(final Long id) {
        return this.floatingRateRepository.findById(id).orElseThrow(() -> new FloatingRateNotFoundException(id));
    }
    public void save(FloatingRate newFloatingRate) {
        this.floatingRateRepository.save(newFloatingRate);
    }
    public void saveAndFlush(FloatingRate floatingRateForUpdate) {
        this.floatingRateRepository.saveAndFlush(floatingRateForUpdate);
    }
}
