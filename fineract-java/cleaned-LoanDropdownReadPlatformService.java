
package org.apache.fineract.portfolio.loanproduct.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanproduct.data.TransactionProcessingStrategyData;
public interface LoanDropdownReadPlatformService {
    List<EnumOptionData> retrieveLoanAmortizationTypeOptions();
    List<EnumOptionData> retrieveLoanInterestTypeOptions();
    List<EnumOptionData> retrieveLoanInterestRateCalculatedInPeriodOptions();
    List<EnumOptionData> retrieveLoanTermFrequencyTypeOptions();
    List<EnumOptionData> retrieveRepaymentFrequencyTypeOptions();
    List<EnumOptionData> retrieveRepaymentFrequencyOptionsForNthDayOfMonth();
    List<EnumOptionData> retrieveRepaymentFrequencyOptionsForDaysOfWeek();
    List<EnumOptionData> retrieveInterestRateFrequencyTypeOptions();
    Collection<TransactionProcessingStrategyData> retreiveTransactionProcessingStrategies();
    List<EnumOptionData> retrieveLoanCycleValueConditionTypeOptions();
    List<EnumOptionData> retrieveInterestRecalculationCompoundingTypeOptions();
    List<EnumOptionData> retrieveInterestRecalculationNthDayTypeOptions();
    List<EnumOptionData> retrieveInterestRecalculationDayOfWeekTypeOptions();
    List<EnumOptionData> retrieveRescheduleStrategyTypeOptions();
    List<EnumOptionData> retrieveInterestRecalculationFrequencyTypeOptions();
    List<EnumOptionData> retrivePreCloseInterestCalculationStrategyOptions();
}
