
package org.apache.fineract.portfolio.interestratechart.service;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.INTERESTRATE_CHART_RESOURCE_NAME;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.descriptionParamName;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.endDateParamName;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.fromDateParamName;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.isPrimaryGroupingByAmountParamName;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartApiConstants.nameParamName;
import static org.apache.fineract.portfolio.interestratechart.InterestRateChartSlabApiConstants.currencyCodeParamName;
import com.google.gson.JsonElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.interestratechart.domain.InterestRateChart;
import org.apache.fineract.portfolio.interestratechart.domain.InterestRateChartFields;
import org.apache.fineract.portfolio.interestratechart.domain.InterestRateChartRepositoryWrapper;
import org.apache.fineract.portfolio.interestratechart.domain.InterestRateChartSlab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InterestRateChartAssembler {
    private final FromJsonHelper fromApiJsonHelper;
    private final InterestRateChartRepositoryWrapper interestRateChartRepositoryWrapper;
    private final InterestRateChartSlabAssembler chartSlabAssembler;
    @Autowired
    public InterestRateChartAssembler(final FromJsonHelper fromApiJsonHelper,
            final InterestRateChartRepositoryWrapper interestRateChartRepositoryWrapper,
            final InterestRateChartSlabAssembler chartSlabAssembler) {
        this.fromApiJsonHelper = fromApiJsonHelper;
        this.interestRateChartRepositoryWrapper = interestRateChartRepositoryWrapper;
        this.chartSlabAssembler = chartSlabAssembler;
    }
    public InterestRateChart assembleFrom(final JsonCommand command) {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(INTERESTRATE_CHART_RESOURCE_NAME);
        final JsonElement element = command.parsedJson();
        final String currencyCode = this.fromApiJsonHelper.extractStringNamed(currencyCodeParamName, element);
        final InterestRateChart newChart = this.assembleFrom(element, currencyCode, baseDataValidator);
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
        return newChart;
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
    public InterestRateChart assembleFrom(final JsonElement element, final String currencyCode,
            final DataValidatorBuilder baseDataValidator) {
        final String name = this.fromApiJsonHelper.extractStringNamed(nameParamName, element);
        final String description = this.fromApiJsonHelper.extractStringNamed(descriptionParamName, element);
        final LocalDate fromDate = this.fromApiJsonHelper.extractLocalDateNamed(fromDateParamName, element);
        final LocalDate toDate = this.fromApiJsonHelper.extractLocalDateNamed(endDateParamName, element);
        Boolean isPrimaryGroupingByAmount = this.fromApiJsonHelper.extractBooleanNamed(isPrimaryGroupingByAmountParamName, element);
        if (isPrimaryGroupingByAmount == null) {
            isPrimaryGroupingByAmount = false;
        }
        final Collection<InterestRateChartSlab> newChartSlabs = this.chartSlabAssembler.assembleChartSlabsFrom(element, currencyCode);
        final InterestRateChartFields fields = InterestRateChartFields.createNew(name, description, fromDate, toDate,
                isPrimaryGroupingByAmount);
        final InterestRateChart newChart = InterestRateChart.createNew(fields, newChartSlabs);
        newChart.validateChartSlabs(baseDataValidator);
        return newChart;
    }
    public InterestRateChart assembleFrom(final Long interestRateChartId) {
        final InterestRateChart interestRateChart = this.interestRateChartRepositoryWrapper
                .findOneWithNotFoundDetection(interestRateChartId);
        return interestRateChart;
    }
}
