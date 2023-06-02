
package org.apache.fineract.portfolio.calendar.domain;
import org.apache.fineract.portfolio.calendar.exception.CalendarInstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CalendarInstanceRepositoryWrapper {
    private final CalendarInstanceRepository repository;
    @Autowired
    public CalendarInstanceRepositoryWrapper(final CalendarInstanceRepository repository) {
        this.repository = repository;
    }
    public CalendarInstance findOneWithNotFoundDetection(final Long CalendarInstanceId) {
        return this.repository.findById(CalendarInstanceId).orElseThrow(() -> new CalendarInstanceNotFoundException(CalendarInstanceId));
    }
    public void save(final CalendarInstance calendatInstance) {
        this.repository.save(calendatInstance);
    }
    public void delete(final CalendarInstance calendatInstance) {
        this.repository.delete(calendatInstance);
    }
    public void saveAndFlush(final CalendarInstance calendatInstance) {
        this.repository.saveAndFlush(calendatInstance);
    }
}
