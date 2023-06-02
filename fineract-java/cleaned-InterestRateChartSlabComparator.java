
package org.apache.fineract.portfolio.interestratechart.domain;
import java.math.BigDecimal;
import java.util.Comparator;
import org.apache.fineract.portfolio.savings.domain.DepositAccountInterestRateChartSlabs;
public class InterestRateChartSlabComparator<T> implements Comparator<T> {
    private final boolean isPrimaryGroupingByAmount;
    public InterestRateChartSlabComparator(final boolean isPrimaryGroupingByAmount) {
        this.isPrimaryGroupingByAmount = isPrimaryGroupingByAmount;
    }
    @Override
    public int compare(final T o1, final T o2) {
        int compareResult = 0;
        InterestRateChartSlabFields slabs1 = null;
        InterestRateChartSlabFields slabs2 = null;
        if (o1 instanceof InterestRateChartSlab) {
            slabs1 = ((InterestRateChartSlab) o1).slabFields();
            slabs2 = ((InterestRateChartSlab) o2).slabFields();
        } else if (o1 instanceof DepositAccountInterestRateChartSlabs) {
            slabs1 = ((DepositAccountInterestRateChartSlabs) o1).slabFields();
            slabs2 = ((DepositAccountInterestRateChartSlabs) o2).slabFields();
        } else {
            return compareResult;
        }
        if (slabs1.isPeriodsSame(slabs2) && slabs1.isAmountSame(slabs2)) {
            compareResult = 0;
        } else {
            if (isPrimaryGroupingByAmount) {
                if (slabs1.isAmountSame(slabs2)) {
                    compareResult = comparePeriods(slabs1, slabs2);
                } else {
                    compareResult = compareAmounts(slabs1, slabs2);
                }
            } else {
                if (slabs1.isPeriodsSame(slabs2)) {
                    compareResult = compareAmounts(slabs1, slabs2);
                } else {
                    compareResult = comparePeriods(slabs1, slabs2);
                }
            }
        }
        return compareResult;
    }
    private int comparePeriods(final InterestRateChartSlabFields slabs1, InterestRateChartSlabFields slabs2) {
        int compareResult = 0;
        Integer periodFrom1 = slabs1.fromPeriod();
        Integer periodFrom2 = slabs2.fromPeriod();
        if (periodFrom1 != null && periodFrom2 != null) {
            compareResult = periodFrom1.compareTo(periodFrom2);
        }
        return compareResult;
    }
    private int compareAmounts(final InterestRateChartSlabFields slabs1, InterestRateChartSlabFields slabs2) {
        int compareResult = 0;
        BigDecimal amountFrom1 = slabs1.getAmountRangeFrom();
        BigDecimal amountFrom2 = slabs2.getAmountRangeFrom();
        if (amountFrom1 != null && amountFrom2 != null) {
            compareResult = amountFrom1.compareTo(amountFrom2);
        }
        return compareResult;
    }
}
