
package org.apache.fineract.organisation.workingdays.domain;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.organisation.workingdays.exception.WorkingDaysNotFoundException;
import org.apache.fineract.organisation.workingdays.service.WorkingDaysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WorkingDaysRepositoryWrapper {
    private final WorkingDaysRepository repository;
    @Autowired
    public WorkingDaysRepositoryWrapper(final WorkingDaysRepository repository) {
        this.repository = repository;
    }
    public WorkingDays findOne() {
        final List<WorkingDays> workingDaysList = this.repository.findAll();
        if (workingDaysList == null || workingDaysList.isEmpty()) {
            throw new WorkingDaysNotFoundException();
        }
        return workingDaysList.get(0);
    }
    public void save(final WorkingDays workingDays) {
        this.repository.save(workingDays);
    }
    public void saveAndFlush(final WorkingDays workingDays) {
        this.repository.saveAndFlush(workingDays);
    }
    public void delete(final WorkingDays workingDays) {
        this.repository.delete(workingDays);
    }
    public boolean isWorkingDay(LocalDate transactionDate) {
        final WorkingDays workingDays = findOne();
        return WorkingDaysUtil.isWorkingDay(workingDays, transactionDate);
    }
}
