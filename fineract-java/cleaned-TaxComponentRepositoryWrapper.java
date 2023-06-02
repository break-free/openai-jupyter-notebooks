
package org.apache.fineract.portfolio.tax.domain;
import org.apache.fineract.portfolio.tax.exception.TaxComponentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TaxComponentRepositoryWrapper {
    private final TaxComponentRepository repository;
    @Autowired
    public TaxComponentRepositoryWrapper(final TaxComponentRepository repository) {
        this.repository = repository;
    }
    public TaxComponent findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new TaxComponentNotFoundException(id));
    }
}
