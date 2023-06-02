
package org.apache.fineract.portfolio.tax.domain;
import org.apache.fineract.portfolio.tax.exception.TaxGroupNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TaxGroupRepositoryWrapper {
    private final TaxGroupRepository repository;
    @Autowired
    public TaxGroupRepositoryWrapper(final TaxGroupRepository repository) {
        this.repository = repository;
    }
    public TaxGroup findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new TaxGroupNotFoundException(id));
    }
}
