
package org.apache.fineract.portfolio.shareproducts.service;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.shareaccounts.service.SharesEnumerations;
import org.apache.fineract.portfolio.shareproducts.SharePeriodFrequencyType;
import org.springframework.stereotype.Service;
@Service
public class ShareProductDropdownReadPlatformServiceImpl implements ShareProductDropdownReadPlatformService {
    @Override
    public Collection<EnumOptionData> retrieveLockinPeriodFrequencyTypeOptions() {
        final List<EnumOptionData> allowedLockinPeriodFrequencyTypeOptions = Arrays.asList(
                SharesEnumerations.lockinPeriodFrequencyType(SharePeriodFrequencyType.DAYS),
                SharesEnumerations.lockinPeriodFrequencyType(SharePeriodFrequencyType.WEEKS),
                SharesEnumerations.lockinPeriodFrequencyType(SharePeriodFrequencyType.MONTHS),
                SharesEnumerations.lockinPeriodFrequencyType(SharePeriodFrequencyType.YEARS));
        return allowedLockinPeriodFrequencyTypeOptions;
    }
    @Override
    public Collection<EnumOptionData> retrieveMinimumActivePeriodFrequencyTypeOptions() {
        final List<EnumOptionData> minimumActivePeriodFrequencyTypeOptions = Arrays
                .asList(SharesEnumerations.lockinPeriodFrequencyType(SharePeriodFrequencyType.DAYS));
        return minimumActivePeriodFrequencyTypeOptions;
    }
}
