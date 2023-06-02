
package org.apache.fineract.portfolio.savings.domain.interest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.domain.LocalDateInterval;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.organisation.monetary.domain.MoneyHelper;
public class EndOfDayBalance {
    private final LocalDate date;
    private final Money openingBalance;
    private final Money endOfDayBalance;
    private final int numberOfDays;
    public static EndOfDayBalance from(final LocalDate date, final Money openingBalance, final Money endOfDayBalance,
            final int numberOfDays) {
        return new EndOfDayBalance(date, openingBalance, endOfDayBalance, numberOfDays);
    }
    public EndOfDayBalance(final LocalDate date, final Money openingBalance, final Money endOfDayBalance, final int numberOfDays) {
        this.date = date;
        this.openingBalance = openingBalance;
        this.endOfDayBalance = endOfDayBalance;
        this.numberOfDays = numberOfDays;
    }
    public LocalDate date() {
        return this.date;
    }
    public Money closingBalance() {
        return this.endOfDayBalance;
    }
    public BigDecimal cumulativeBalance(final BigDecimal interestToCompound) {
        final BigDecimal daysAsBigDecimal = BigDecimal.valueOf(this.numberOfDays);
        final BigDecimal realBalanceForInterestCalculation = this.endOfDayBalance.getAmount().add(interestToCompound);
        return realBalanceForInterestCalculation.multiply(daysAsBigDecimal, MathContext.DECIMAL64).setScale(9,
                MoneyHelper.getRoundingMode());
    }
    public BigDecimal calculateInterestOnBalance(final BigDecimal interestToCompound, final BigDecimal interestRateAsFraction,
            final long daysInYear, final BigDecimal minBalanceForInterestCalculation, final BigDecimal overdraftInterestRateAsFraction,
            final BigDecimal minOverdraftForInterestCalculation) {
        BigDecimal interest = BigDecimal.ZERO.setScale(9, MoneyHelper.getRoundingMode());
        final BigDecimal realBalanceForInterestCalculation = this.endOfDayBalance.getAmount().add(interestToCompound);
        if (realBalanceForInterestCalculation.compareTo(BigDecimal.ZERO) >= 0) {
            if (realBalanceForInterestCalculation.compareTo(minBalanceForInterestCalculation) >= 0) {
                final BigDecimal multiplicand = BigDecimal.ONE.divide(BigDecimal.valueOf(daysInYear), MathContext.DECIMAL64);
                final BigDecimal dailyInterestRate = interestRateAsFraction.multiply(multiplicand, MathContext.DECIMAL64);
                final BigDecimal periodicInterestRate = dailyInterestRate.multiply(BigDecimal.valueOf(this.numberOfDays),
                        MathContext.DECIMAL64);
                interest = realBalanceForInterestCalculation.multiply(periodicInterestRate, MathContext.DECIMAL64).setScale(9,
                        MoneyHelper.getRoundingMode());
            }
        } else {
            if (realBalanceForInterestCalculation.compareTo(minOverdraftForInterestCalculation.negate()) < 0) {
                final BigDecimal multiplicand = BigDecimal.ONE.divide(BigDecimal.valueOf(daysInYear), MathContext.DECIMAL64);
                final BigDecimal dailyInterestRate = overdraftInterestRateAsFraction.multiply(multiplicand, MathContext.DECIMAL64);
                final BigDecimal periodicInterestRate = dailyInterestRate.multiply(BigDecimal.valueOf(this.numberOfDays),
                        MathContext.DECIMAL64);
                interest = realBalanceForInterestCalculation.multiply(periodicInterestRate, MathContext.DECIMAL64).setScale(9,
                        MoneyHelper.getRoundingMode());
            }
        }
        return interest;
    }
    public BigDecimal calculateInterestOnBalanceAndInterest(final BigDecimal interestToCompound, final BigDecimal interestRateAsFraction,
            final long daysInYear, final BigDecimal minBalanceForInterestCalculation, final BigDecimal overdraftInterestRateAsFraction,
            final BigDecimal minOverdraftForInterestCalculation) {
        final BigDecimal multiplicand = BigDecimal.ONE.divide(BigDecimal.valueOf(daysInYear), MathContext.DECIMAL64);
        final BigDecimal presentValue = this.endOfDayBalance.getAmount().add(interestToCompound);
        BigDecimal futureValue = presentValue.setScale(9, MoneyHelper.getRoundingMode());
        if (presentValue.compareTo(BigDecimal.ZERO) >= 0) {
            if (presentValue.compareTo(minBalanceForInterestCalculation) >= 0) {
                final BigDecimal r = interestRateAsFraction.multiply(multiplicand);
                final BigDecimal interestRateForCompoundingPeriodPlusOne = BigDecimal.ONE.add(r);
                final double interestRateForCompoundingPeriodPowered = Math.pow(interestRateForCompoundingPeriodPlusOne.doubleValue(),
                        Integer.valueOf(this.numberOfDays).doubleValue());
                futureValue = presentValue.multiply(BigDecimal.valueOf(interestRateForCompoundingPeriodPowered), MathContext.DECIMAL64)
                        .setScale(9, MoneyHelper.getRoundingMode());
            }
        } else {
            if (presentValue.compareTo(minOverdraftForInterestCalculation.negate()) < 0) {
                final BigDecimal r = overdraftInterestRateAsFraction.multiply(multiplicand);
                final BigDecimal interestRateForCompoundingPeriodPlusOne = BigDecimal.ONE.add(r);
                final double interestRateForCompoundingPeriodPowered = Math.pow(interestRateForCompoundingPeriodPlusOne.doubleValue(),
                        Integer.valueOf(this.numberOfDays).doubleValue());
                futureValue = presentValue.multiply(BigDecimal.valueOf(interestRateForCompoundingPeriodPowered), MathContext.DECIMAL64)
                        .setScale(9, MoneyHelper.getRoundingMode());
            }
        }
        return futureValue.subtract(presentValue);
    }
    public EndOfDayBalance upTo(final LocalDateInterval compoundingPeriodInterval, final LocalDate upToInterestCalculationDate) {
        Money startingBalance = this.openingBalance;
        LocalDate balanceStartDate = this.date;
        LocalDate oldBalanceEndDate = this.date.plusDays(this.numberOfDays - 1);
        int daysOfBalance = this.numberOfDays;
        if (this.date.isBefore(compoundingPeriodInterval.startDate())) {
            balanceStartDate = compoundingPeriodInterval.startDate();
            startingBalance = this.endOfDayBalance;
            final LocalDateInterval balancePeriodInterval = LocalDateInterval.create(balanceStartDate, oldBalanceEndDate);
            daysOfBalance = balancePeriodInterval.daysInPeriodInclusiveOfEndDate();
        }
        LocalDate balanceEndDate = balanceStartDate.plusDays(daysOfBalance - 1);
        if (balanceEndDate.isAfter(compoundingPeriodInterval.endDate())) {
            balanceEndDate = compoundingPeriodInterval.endDate();
            final LocalDateInterval balancePeriodInterval = LocalDateInterval.create(balanceStartDate, balanceEndDate);
            daysOfBalance = balancePeriodInterval.daysInPeriodInclusiveOfEndDate();
        }
        if (balanceEndDate.isAfter(upToInterestCalculationDate)) {
            balanceEndDate = upToInterestCalculationDate;
            final LocalDateInterval balancePeriodInterval = LocalDateInterval.create(balanceStartDate, balanceEndDate);
            daysOfBalance = balancePeriodInterval.daysInPeriodInclusiveOfEndDate();
        }
        return new EndOfDayBalance(balanceStartDate, startingBalance, this.endOfDayBalance, daysOfBalance);
    }
    public boolean contains(final LocalDateInterval compoundingPeriodInterval) {
        final LocalDate balanceUpToDate = this.date.plusDays(this.numberOfDays - 1);
        final LocalDateInterval balanceInterval = LocalDateInterval.create(this.date, balanceUpToDate);
        return balanceInterval.containsPortionOf(compoundingPeriodInterval);
    }
    public Integer getNumberOfDays() {
        return Integer.valueOf(this.numberOfDays);
    }
}
