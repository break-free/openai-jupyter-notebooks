
package org.apache.fineract.portfolio.shareproducts.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface ShareProductDropdownReadPlatformService {
    Collection<EnumOptionData> retrieveLockinPeriodFrequencyTypeOptions();
    Collection<EnumOptionData> retrieveMinimumActivePeriodFrequencyTypeOptions();
}
