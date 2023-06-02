
package org.apache.fineract.organisation.holiday.domain;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.organisation.holiday.exception.HolidayNotFoundException;
import org.apache.fineract.organisation.holiday.service.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HolidayRepositoryWrapper {
    private final HolidayRepository repository;
    @Autowired
    public HolidayRepositoryWrapper(final HolidayRepository repository) {
        this.repository = repository;
    }
    public Holiday findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new HolidayNotFoundException(id));
    }
    public void save(final Holiday holiday) {
        this.repository.save(holiday);
    }
    public void save(final Iterable<Holiday> holidays) {
        this.repository.saveAll(holidays);
    }
    public void saveAndFlush(final Holiday holiday) {
        this.repository.saveAndFlush(holiday);
    }
    public void delete(final Holiday holiday) {
        this.repository.delete(holiday);
    }
    public List<Holiday> findByOfficeIdAndGreaterThanDate(final Long officeId, final LocalDate date) {
        return this.repository.findByOfficeIdAndGreaterThanDate(officeId, date, HolidayStatusType.ACTIVE.getValue());
    }
    public List<Holiday> findUnprocessed() {
        return this.repository.findUnprocessed(HolidayStatusType.ACTIVE.getValue());
    }
    public boolean isHoliday(Long officeId, LocalDate transactionDate) {
        final List<Holiday> holidays = findByOfficeIdAndGreaterThanDate(officeId, transactionDate);
        return HolidayUtil.isHoliday(transactionDate, holidays);
    }
}
