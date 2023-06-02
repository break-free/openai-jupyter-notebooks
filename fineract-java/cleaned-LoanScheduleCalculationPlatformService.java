
package org.apache.fineract.portfolio.loanaccount.loanschedule.service;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanScheduleModel;
public interface LoanScheduleCalculationPlatformService {
    LoanScheduleModel calculateLoanSchedule(JsonQuery query, Boolean validateParams);
    void updateFutureSchedule(LoanScheduleData loanScheduleData, Long loanId);
    LoanScheduleData generateLoanScheduleForVariableInstallmentRequest(Long loanId, String json);
}
