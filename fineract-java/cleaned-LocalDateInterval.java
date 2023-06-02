
package org.apache.fineract.infrastructure.core.domain;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class LocalDateInterval {
    private final LocalDate startDate;
    private final LocalDate endDate;
    public static LocalDateInterval create(final LocalDate startDate, final LocalDate endDate) {
        return new LocalDateInterval(startDate, endDate);
    }
    public LocalDateInterval(final LocalDate startDate, final LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public LocalDate startDate() {
        return this.startDate;
    }
    public LocalDate endDate() {
        return this.endDate;
    }
    public Integer daysInPeriodInclusiveOfEndDate() {
        return daysBetween() + 1;
    }
    private Integer daysBetween() {
        return Math.toIntExact(ChronoUnit.DAYS.between(this.startDate, this.endDate));
    }
    public boolean containsPortionOf(final LocalDateInterval interval) {
        return contains(interval.startDate) || contains(interval.endDate);
    }
    public boolean contains(final LocalDateInterval interval) {
        return contains(interval.startDate) && contains(interval.endDate);
    }
    public boolean contains(final LocalDate target) {
        return isBetweenInclusive(this.startDate, this.endDate, target);
    }
    private boolean isBetweenInclusive(final LocalDate start, final LocalDate end, final LocalDate target) {
        return !target.isBefore(start) && !target.isAfter(end);
    }
    public boolean fallsBefore(final LocalDate dateToCheck) {
        return this.endDate.isBefore(dateToCheck);
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
