
package org.apache.fineract.portfolio.group.domain;
import java.time.LocalDate;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.portfolio.group.exception.GroupNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GroupRepositoryWrapper {
    private final GroupRepository repository;
    @Autowired
    public GroupRepositoryWrapper(final GroupRepository repository) {
        this.repository = repository;
    }
    public Group findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
    }
    public Group findByOfficeWithNotFoundDetection(final Long id, final Office office) {
        final Group group = findOneWithNotFoundDetection(id);
        if (!group.getOffice().getId().equals(office.getId())) {
            throw new GroupNotFoundException(id);
        }
        return group;
    }
    public void save(final Group entity) {
        this.repository.save(entity);
    }
    public void saveAndFlush(final Group entity) {
        this.repository.saveAndFlush(entity);
    }
    public void delete(final Group entity) {
        this.repository.delete(entity);
    }
    public void flush() {
        this.repository.flush();
    }
    public LocalDate retrieveSubmittedOndate(final Long groupId) {
        return this.repository.retrieveGroupTypeSubmitteOndDate(groupId);
    }
}
