
package org.apache.fineract.portfolio.loanaccount.loanschedule.service;
import java.util.Collection;
import org.apache.fineract.portfolio.loanaccount.data.DisbursementData;
import org.apache.fineract.portfolio.loanaccount.data.RepaymentScheduleRelatedLoanData;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
public interface LoanScheduleHistoryReadPlatformService {
    Integer fetchCurrentVersionNumber(Long loanId);
    LoanScheduleData retrieveRepaymentArchiveSchedule(Long loanId, RepaymentScheduleRelatedLoanData repaymentScheduleRelatedLoanData,
            Collection<DisbursementData> disbursementData);
}
