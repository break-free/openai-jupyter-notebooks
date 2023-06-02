
package org.apache.fineract.portfolio.loanaccount.service;
import java.util.Map;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface LoanSchedularService {
    void applyChargeForOverdueLoans() throws JobExecutionException;
    void recalculateInterest() throws JobExecutionException;
    void recalculateInterest(@SuppressWarnings("unused") Map<String, String> jobParameters);
}
