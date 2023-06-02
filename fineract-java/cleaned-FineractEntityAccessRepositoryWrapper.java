
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.apache.fineract.infrastructure.entityaccess.exception.FineractEntityAccessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FineractEntityAccessRepositoryWrapper {
    private final FineractEntityAccessRepository repository;
    @Autowired
    public FineractEntityAccessRepositoryWrapper(final FineractEntityAccessRepository repository) {
        this.repository = repository;
    }
    public FineractEntityAccess findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new FineractEntityAccessNotFoundException(id));
    }
    public void save(final FineractEntityAccess fineractEntityAccess) {
        this.repository.save(fineractEntityAccess);
    }
    public void saveAndFlush(final FineractEntityAccess fineractEntityAccess) {
        this.repository.saveAndFlush(fineractEntityAccess);
    }
    public void delete(final FineractEntityAccess fineractEntityAccess) {
        this.repository.delete(fineractEntityAccess);
    }
}
