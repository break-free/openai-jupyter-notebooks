
package org.apache.fineract.organisation.teller.domain;
import org.apache.fineract.organisation.teller.exception.TellerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CashierRepositoryWrapper {
    private final CashierRepository repository;
    @Autowired
    public CashierRepositoryWrapper(final CashierRepository repository) {
        this.repository = repository;
    }
    public Cashier findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new TellerNotFoundException(id));
    }
}
