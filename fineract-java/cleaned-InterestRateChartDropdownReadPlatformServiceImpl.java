
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.springframework.stereotype.Service;
@Service
public class InterestRateChartDropdownReadPlatformServiceImpl implements InterestRateChartDropdownReadPlatformService {
    @Override
    public Collection<EnumOptionData> retrievePeriodTypeOptions() {
        return InterestRateChartEnumerations.periodType(PeriodFrequencyType.values());
    }
}
