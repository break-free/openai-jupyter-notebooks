
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface InterestRateChartDropdownReadPlatformService {
    Collection<EnumOptionData> retrievePeriodTypeOptions();
}
