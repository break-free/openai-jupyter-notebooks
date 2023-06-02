
package org.apache.fineract.portfolio.interestratechart.data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class InterestRateChartData {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate fromDate;
    private final LocalDate endDate;
    private final Long productId;
    private final String productName;
    private final boolean isPrimaryGroupingByAmount;
    private Collection<InterestRateChartSlabData> chartSlabs;
    private final Collection<EnumOptionData> periodTypes;
    private final Collection<EnumOptionData> entityTypeOptions;
    private final Collection<EnumOptionData> attributeNameOptions;
    private final Collection<EnumOptionData> conditionTypeOptions;
    private final Collection<EnumOptionData> incentiveTypeOptions;
    private final Collection<CodeValueData> genderOptions;
    private final Collection<CodeValueData> clientTypeOptions;
    private final Collection<CodeValueData> clientClassificationOptions;
    public static InterestRateChartData instance(Long id, String name, String description, LocalDate fromDate, LocalDate endDate,
            boolean isPrimaryGroupingByAmount, Long savingsProductId, String savingsProductName) {
        Collection<EnumOptionData> periodTypes = null;
        Collection<InterestRateChartSlabData> chartSlabs = null;
        final Collection<EnumOptionData> entityTypeOptions = null;
        final Collection<EnumOptionData> attributeNameOptions = null;
        final Collection<EnumOptionData> conditionTypeOptions = null;
        final Collection<EnumOptionData> incentiveTypeOptions = null;
        final Collection<CodeValueData> genderOptions = null;
        final Collection<CodeValueData> clientTypeOptions = null;
        final Collection<CodeValueData> clientClassificationOptions = null;
        return new InterestRateChartData(id, name, description, fromDate, endDate, isPrimaryGroupingByAmount, savingsProductId,
                savingsProductName, chartSlabs, periodTypes, entityTypeOptions, attributeNameOptions, conditionTypeOptions,
                incentiveTypeOptions, genderOptions, clientTypeOptions, clientClassificationOptions);
    }
    public static InterestRateChartData withSlabs(InterestRateChartData interestRateChartData,
            Collection<InterestRateChartSlabData> chartSlabs) {
        return new InterestRateChartData(interestRateChartData.id, interestRateChartData.name, interestRateChartData.description,
                interestRateChartData.fromDate, interestRateChartData.endDate, interestRateChartData.isPrimaryGroupingByAmount,
                interestRateChartData.productId, interestRateChartData.productName, chartSlabs, interestRateChartData.periodTypes,
                interestRateChartData.entityTypeOptions, interestRateChartData.attributeNameOptions,
                interestRateChartData.conditionTypeOptions, interestRateChartData.incentiveTypeOptions, interestRateChartData.genderOptions,
                interestRateChartData.clientTypeOptions, interestRateChartData.clientClassificationOptions);
    }
    public static InterestRateChartData withTemplate(InterestRateChartData interestRateChartData, Collection<EnumOptionData> periodTypes,
            final Collection<EnumOptionData> entityTypeOptions, final Collection<EnumOptionData> attributeNameOptions,
            final Collection<EnumOptionData> conditionTypeOptions, final Collection<EnumOptionData> incentiveTypeOptions,
            final Collection<CodeValueData> genderOptions, final Collection<CodeValueData> clientTypeOptions,
            final Collection<CodeValueData> clientClassificationOptions) {
        return new InterestRateChartData(interestRateChartData.id, interestRateChartData.name, interestRateChartData.description,
                interestRateChartData.fromDate, interestRateChartData.endDate, interestRateChartData.isPrimaryGroupingByAmount,
                interestRateChartData.productId, interestRateChartData.productName, interestRateChartData.chartSlabs, periodTypes,
                entityTypeOptions, attributeNameOptions, conditionTypeOptions, incentiveTypeOptions, genderOptions, clientTypeOptions,
                clientClassificationOptions);
    }
    public static InterestRateChartData template(Collection<EnumOptionData> periodTypes, final Collection<EnumOptionData> entityTypeOptions,
            final Collection<EnumOptionData> attributeNameOptions, final Collection<EnumOptionData> conditionTypeOptions,
            final Collection<EnumOptionData> incentiveTypeOptions, final Collection<CodeValueData> genderOptions,
            final Collection<CodeValueData> clientTypeOptions, final Collection<CodeValueData> clientClassificationOptions) {
        final Long id = null;
        final String name = null;
        final String description = null;
        final LocalDate fromDate = null;
        final LocalDate endDate = null;
        final boolean isPrimaryGroupingByAmount = false;
        final Long savingsProductId = null;
        final String savingsProductName = null;
        final Collection<InterestRateChartSlabData> chartSlabs = null;
        return new InterestRateChartData(id, name, description, fromDate, endDate, isPrimaryGroupingByAmount, savingsProductId,
                savingsProductName, chartSlabs, periodTypes, entityTypeOptions, attributeNameOptions, conditionTypeOptions,
                incentiveTypeOptions, genderOptions, clientTypeOptions, clientClassificationOptions);
    }
    private InterestRateChartData(Long id, String name, String description, LocalDate fromDate, LocalDate endDate,
            boolean isPrimaryGroupingByAmount, Long savingsProductId, String savingsProductName,
            Collection<InterestRateChartSlabData> chartSlabs, Collection<EnumOptionData> periodTypes,
            final Collection<EnumOptionData> entityTypeOptions, final Collection<EnumOptionData> attributeNameOptions,
            final Collection<EnumOptionData> conditionTypeOptions, final Collection<EnumOptionData> incentiveTypeOptions,
            final Collection<CodeValueData> genderOptions, final Collection<CodeValueData> clientTypeOptions,
            final Collection<CodeValueData> clientClassificationOptions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.isPrimaryGroupingByAmount = isPrimaryGroupingByAmount;
        this.chartSlabs = chartSlabs;
        this.productId = savingsProductId;
        this.productName = savingsProductName;
        this.periodTypes = periodTypes;
        this.attributeNameOptions = attributeNameOptions;
        this.entityTypeOptions = entityTypeOptions;
        this.conditionTypeOptions = conditionTypeOptions;
        this.incentiveTypeOptions = incentiveTypeOptions;
        this.genderOptions = genderOptions;
        this.clientTypeOptions = clientTypeOptions;
        this.clientClassificationOptions = clientClassificationOptions;
    }
    public void addChartSlab(final InterestRateChartSlabData chartSlab) {
        if (this.chartSlabs == null) {
            this.chartSlabs = new ArrayList<>();
        }
        this.chartSlabs.add(chartSlab);
    }
    public boolean isFromDateAfter(final LocalDate compareDate) {
        return (compareDate == null) ? false : this.fromDate.isAfter(compareDate);
    }
    public LocalDate endDate() {
        return this.endDate;
    }
    public LocalDate fromDate() {
        return this.fromDate;
    }
    public String name() {
        return this.name;
    }
    public String description() {
        return this.description;
    }
    public Collection<InterestRateChartSlabData> chartSlabs() {
        return this.chartSlabs;
    }
    public Collection<EnumOptionData> periodTypes() {
        return this.periodTypes;
    }
    public Collection<EnumOptionData> entityTypeOptions() {
        return this.entityTypeOptions;
    }
    public Collection<EnumOptionData> attributeNameOptions() {
        return this.attributeNameOptions;
    }
    public Collection<EnumOptionData> conditionTypeOptions() {
        return this.conditionTypeOptions;
    }
    public Collection<EnumOptionData> incentiveTypeOptions() {
        return this.incentiveTypeOptions;
    }
    public Collection<CodeValueData> genderOptions() {
        return this.genderOptions;
    }
    public Collection<CodeValueData> clientTypeOptions() {
        return this.clientTypeOptions;
    }
    public Collection<CodeValueData> clientClassificationOptions() {
        return this.clientClassificationOptions;
    }
    public boolean isPrimaryGroupingByAmount() {
        return this.isPrimaryGroupingByAmount;
    }
}
