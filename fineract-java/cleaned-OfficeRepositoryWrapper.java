
package org.apache.fineract.organisation.office.domain;
import org.apache.fineract.organisation.office.exception.OfficeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OfficeRepositoryWrapper {
    private final OfficeRepository repository;
    @Autowired
    public OfficeRepositoryWrapper(final OfficeRepository repository) {
        this.repository = repository;
    }
    public Office findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new OfficeNotFoundException(id));
    }
    @Transactional(readOnly = true)
    public Office findOfficeHierarchy(final Long id) {
        final Office office = this.repository.findById(id).orElseThrow(() -> new OfficeNotFoundException(id));
        office.loadLazyCollections();
        return office;
    }
    public Office save(final Office entity) {
        return this.repository.save(entity);
    }
    public Office saveAndFlush(final Office entity) {
        return this.repository.saveAndFlush(entity);
    }
    public void delete(final Office entity) {
        this.repository.delete(entity);
    }
}
