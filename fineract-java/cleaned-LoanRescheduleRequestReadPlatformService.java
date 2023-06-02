
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.service;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.data.LoanRescheduleRequestData;
public interface LoanRescheduleRequestReadPlatformService {
    List<LoanRescheduleRequestData> readLoanRescheduleRequests(Long loanId);
    LoanRescheduleRequestData readLoanRescheduleRequest(Long requestId);
    List<LoanRescheduleRequestData> readLoanRescheduleRequests(Long loanId, Integer statusEnum);
    LoanRescheduleRequestData retrieveAllRescheduleReasons(String loanRescheduleReason);
    List<LoanRescheduleRequestData> retrieveAllRescheduleRequests(String command);
}
