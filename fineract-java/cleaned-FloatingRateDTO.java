
package org.apache.fineract.portfolio.floatingrates.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
public class FloatingRateDTO {
    private final boolean isFloatingInterestRate;
    private final LocalDate startDate;
    private BigDecimal interestRateDiff;
    private BigDecimal actualInterestRateDiff;
    private final Collection<FloatingRatePeriodData> baseLendingRatePeriods;
    public FloatingRateDTO(final boolean isFloatingInterestRate, final LocalDate startDate, final BigDecimal interestRateDiff,
            final Collection<FloatingRatePeriodData> baseLendingRatePeriods) {
        this.isFloatingInterestRate = isFloatingInterestRate;
        this.startDate = startDate;
        this.interestRateDiff = interestRateDiff;
        this.actualInterestRateDiff = interestRateDiff;
        this.baseLendingRatePeriods = baseLendingRatePeriods;
    }
    public BigDecimal fetchBaseRate(LocalDate date) {
        BigDecimal rate = null;
        for (FloatingRatePeriodData periodData : this.baseLendingRatePeriods) {
            final LocalDate periodFromDate = periodData.getFromDate();
            if (periodFromDate.isBefore(date) || periodFromDate.isEqual(date)) {
                rate = periodData.getInterestRate();
                break;
            }
        }
        return rate;
    }
    public void addInterestRateDiff(final BigDecimal diff) {
        this.interestRateDiff = this.interestRateDiff.add(diff);
    }
    public boolean isFloatingInterestRate() {
        return this.isFloatingInterestRate;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public BigDecimal getInterestRateDiff() {
        return this.interestRateDiff;
    }
    public Collection<FloatingRatePeriodData> getBaseLendingRatePeriods() {
        return this.baseLendingRatePeriods;
    }
    public void resetInterestRateDiff() {
        this.interestRateDiff = this.actualInterestRateDiff;
    }
}
