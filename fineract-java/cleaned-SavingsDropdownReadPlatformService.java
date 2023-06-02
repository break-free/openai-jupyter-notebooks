
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface SavingsDropdownReadPlatformService {
    Collection<EnumOptionData> retrieveLockinPeriodFrequencyTypeOptions();
    Collection<EnumOptionData> retrieveCompoundingInterestPeriodTypeOptions();
    Collection<EnumOptionData> retrieveInterestPostingPeriodTypeOptions();
    Collection<EnumOptionData> retrieveInterestCalculationTypeOptions();
    Collection<EnumOptionData> retrieveInterestCalculationDaysInYearTypeOptions();
    Collection<EnumOptionData> retrievewithdrawalFeeTypeOptions();
}
