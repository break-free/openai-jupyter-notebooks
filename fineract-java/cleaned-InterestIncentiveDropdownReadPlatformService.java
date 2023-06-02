
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface InterestIncentiveDropdownReadPlatformService {
    Collection<EnumOptionData> retrieveEntityTypeOptions();
    Collection<EnumOptionData> retrieveAttributeNameOptions();
    Collection<EnumOptionData> retrieveConditionTypeOptions();
    Collection<EnumOptionData> retrieveIncentiveTypeOptions();
}
