
package org.apache.fineract.portfolio.tax.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.organisation.monetary.domain.MoneyHelper;
import org.apache.fineract.portfolio.tax.data.TaxComponentData;
import org.apache.fineract.portfolio.tax.data.TaxGroupMappingsData;
import org.apache.fineract.portfolio.tax.domain.TaxComponent;
import org.apache.fineract.portfolio.tax.domain.TaxGroupMappings;
public final class TaxUtils {
    private TaxUtils() {
    }
    public static Map<TaxComponent, BigDecimal> splitTax(final BigDecimal amount, final LocalDate date,
            final Set<TaxGroupMappings> taxGroupMappings, final int scale) {
        Map<TaxComponent, BigDecimal> map = new HashMap<>(3);
        if (amount != null) {
            final double amountVal = amount.doubleValue();
            double cent_percentage = Double.parseDouble("100.0");
            for (TaxGroupMappings groupMappings : taxGroupMappings) {
                if (groupMappings.occursOnDayFromAndUpToAndIncluding(date)) {
                    TaxComponent component = groupMappings.getTaxComponent();
                    BigDecimal percentage = component.getApplicablePercentage(date);
                    if (percentage != null) {
                        double percentageVal = percentage.doubleValue();
                        double tax = amountVal * percentageVal / cent_percentage;
                        map.put(component, BigDecimal.valueOf(tax).setScale(scale, MoneyHelper.getRoundingMode()));
                    }
                }
            }
        }
        return map;
    }
    public static Map<TaxComponentData, BigDecimal> splitTaxData(final BigDecimal amount, final LocalDate date,
            final Set<TaxGroupMappingsData> taxGroupMappings, final int scale) {
        Map<TaxComponentData, BigDecimal> map = new HashMap<>(3);
        if (amount != null) {
            final double amountVal = amount.doubleValue();
            double cent_percentage = Double.parseDouble("100.0");
            for (TaxGroupMappingsData groupMappings : taxGroupMappings) {
                if (groupMappings.occursOnDayFromAndUpToAndIncluding(date)) {
                    TaxComponentData component = groupMappings.getTaxComponent();
                    BigDecimal percentage = component.getApplicablePercentage(date);
                    if (percentage != null) {
                        double percentageVal = percentage.doubleValue();
                        double tax = amountVal * percentageVal / cent_percentage;
                        map.put(component, BigDecimal.valueOf(tax).setScale(scale, MoneyHelper.getRoundingMode()));
                    }
                }
            }
        }
        return map;
    }
    public static BigDecimal incomeAmount(final BigDecimal amount, final LocalDate date, final Set<TaxGroupMappings> taxGroupMappings,
            final int scale) {
        Map<TaxComponent, BigDecimal> map = splitTax(amount, date, taxGroupMappings, scale);
        return incomeAmount(amount, map);
    }
    public static BigDecimal incomeAmount(final BigDecimal amount, final Map<TaxComponent, BigDecimal> map) {
        BigDecimal totalTax = totalTaxAmount(map);
        return amount.subtract(totalTax);
    }
    public static BigDecimal totalTaxAmount(final Map<TaxComponent, BigDecimal> map) {
        BigDecimal totalTax = BigDecimal.ZERO;
        for (BigDecimal tax : map.values()) {
            totalTax = totalTax.add(tax);
        }
        return totalTax;
    }
    public static BigDecimal totalTaxDataAmount(final Map<TaxComponentData, BigDecimal> map) {
        BigDecimal totalTax = BigDecimal.ZERO;
        for (BigDecimal tax : map.values()) {
            totalTax = totalTax.add(tax);
        }
        return totalTax;
    }
    public static BigDecimal addTax(final BigDecimal amount, final LocalDate date, final List<TaxGroupMappings> taxGroupMappings,
            final int scale) {
        BigDecimal totalAmount = null;
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            double percentageVal = 0;
            double amountVal = amount.doubleValue();
            double cent_percentage = Double.parseDouble("100.0");
            for (TaxGroupMappings groupMappings : taxGroupMappings) {
                if (groupMappings.occursOnDayFromAndUpToAndIncluding(date)) {
                    TaxComponent component = groupMappings.getTaxComponent();
                    BigDecimal percentage = component.getApplicablePercentage(date);
                    if (percentage != null) {
                        percentageVal = percentageVal + percentage.doubleValue();
                    }
                }
            }
            double total = amountVal * cent_percentage / (cent_percentage - percentageVal);
            totalAmount = BigDecimal.valueOf(total).setScale(scale, MoneyHelper.getRoundingMode());
        }
        return totalAmount;
    }
}
