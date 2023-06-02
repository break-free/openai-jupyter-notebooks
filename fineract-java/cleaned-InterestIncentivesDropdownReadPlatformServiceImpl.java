
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.common.domain.ConditionType;
import org.apache.fineract.portfolio.common.service.CommonEnumerations;
import org.apache.fineract.portfolio.interestratechart.incentive.InterestIncentiveAttributeName;
import org.apache.fineract.portfolio.interestratechart.incentive.InterestIncentiveEntityType;
import org.apache.fineract.portfolio.interestratechart.incentive.InterestIncentiveType;
import org.springframework.stereotype.Service;
@Service
public class InterestIncentivesDropdownReadPlatformServiceImpl implements InterestIncentiveDropdownReadPlatformService {
    @Override
    public Collection<EnumOptionData> retrieveEntityTypeOptions() {
        return InterestIncentivesEnumerations.entityType(InterestIncentiveEntityType.values());
    }
    @Override
    public Collection<EnumOptionData> retrieveAttributeNameOptions() {
        return InterestIncentivesEnumerations.attributeName(InterestIncentiveAttributeName.values());
    }
    @Override
    public Collection<EnumOptionData> retrieveConditionTypeOptions() {
        return CommonEnumerations.conditionType(ConditionType.values(), "incentive");
    }
    @Override
    public Collection<EnumOptionData> retrieveIncentiveTypeOptions() {
        return InterestIncentivesEnumerations.incentiveType(InterestIncentiveType.values());
    }
}
