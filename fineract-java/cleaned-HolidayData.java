
package org.apache.fineract.organisation.holiday.data;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class HolidayData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final String description;
    @SuppressWarnings("unused")
    private final LocalDate fromDate;
    @SuppressWarnings("unused")
    private final LocalDate toDate;
    @SuppressWarnings("unused")
    private final LocalDate repaymentsRescheduledTo;
    @SuppressWarnings("unused")
    private final Long officeId;
    @SuppressWarnings("unused")
    private final EnumOptionData status;
    private final Integer reschedulingType;
    public HolidayData(final Long id, final String name, final String description, final LocalDate fromDate, final LocalDate toDate,
            final LocalDate repaymentsRescheduledTo, final EnumOptionData status, final Integer reschedulingType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.repaymentsRescheduledTo = repaymentsRescheduledTo;
        this.officeId = null;
        this.status = status;
        this.reschedulingType = reschedulingType;
    }
}
