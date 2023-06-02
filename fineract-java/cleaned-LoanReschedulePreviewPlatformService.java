
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.service;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanScheduleModel;
public interface LoanReschedulePreviewPlatformService {
    LoanScheduleModel previewLoanReschedule(Long requestId);
}
