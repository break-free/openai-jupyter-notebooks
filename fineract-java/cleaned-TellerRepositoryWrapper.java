
package org.apache.fineract.organisation.teller.domain;
import org.apache.fineract.organisation.teller.exception.TellerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TellerRepositoryWrapper {
    private final TellerRepository repository;
    @Autowired
    public TellerRepositoryWrapper(final TellerRepository repository) {
        this.repository = repository;
    }
    @Transactional(readOnly = true)
    public Teller findOneWithNotFoundDetection(final Long id) {
        final Teller teller = this.repository.findById(id).orElseThrow(() -> new TellerNotFoundException(id));
        teller.initializeLazyCollections();
        return teller;
    }
    public Teller save(final Teller teller) {
        return this.repository.save(teller);
    }
    public Teller saveAndFlush(final Teller teller) {
        return this.repository.saveAndFlush(teller);
    }
    public void delete(final Teller teller) {
        this.repository.delete(teller);
    }
}
