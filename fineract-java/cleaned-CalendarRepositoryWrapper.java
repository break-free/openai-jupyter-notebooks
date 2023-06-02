
package org.apache.fineract.portfolio.calendar.domain;
import org.apache.fineract.portfolio.calendar.exception.CalendarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CalendarRepositoryWrapper {
    private final CalendarRepository repository;
    @Autowired
    public CalendarRepositoryWrapper(final CalendarRepository repository) {
        this.repository = repository;
    }
    public Calendar findOneWithNotFoundDetection(final Long calendarId) {
        return this.repository.findById(calendarId).orElseThrow(() -> new CalendarNotFoundException(calendarId));
    }
    public void save(final Calendar calendar) {
        this.repository.save(calendar);
    }
    public void delete(final Calendar calendar) {
        this.repository.delete(calendar);
    }
    public void saveAndFlush(final Calendar calendar) {
        this.repository.saveAndFlush(calendar);
    }
}
