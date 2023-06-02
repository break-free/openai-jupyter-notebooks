
package org.apache.fineract.organisation.staff.domain;
import org.apache.fineract.organisation.staff.exception.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StaffRepositoryWrapper {
    private final StaffRepository repository;
    @Autowired
    public StaffRepositoryWrapper(final StaffRepository repository) {
        this.repository = repository;
    }
    public Staff findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new StaffNotFoundException(id));
    }
    public Staff findByOfficeWithNotFoundDetection(final Long staffId, final Long officeId) {
        final Staff staff = this.repository.findByOffice(staffId, officeId);
        if (staff == null) {
            throw new StaffNotFoundException(staffId);
        }
        return staff;
    }
    public Staff findByOfficeHierarchyWithNotFoundDetection(final Long staffId, final String hierarchy) {
        final Staff staff = this.repository.findById(staffId).orElseThrow(() -> new StaffNotFoundException(staffId));
        final String staffhierarchy = staff.office().getHierarchy();
        if (!hierarchy.startsWith(staffhierarchy)) {
            throw new StaffNotFoundException(staffId);
        }
        return staff;
    }
    public void save(final Staff staff) {
        this.repository.save(staff);
    }
}
