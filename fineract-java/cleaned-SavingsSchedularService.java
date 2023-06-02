
package org.apache.fineract.portfolio.savings.service;
import java.util.Map;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface SavingsSchedularService {
    void postInterestForAccounts(Map<String, String> jobParameters) throws JobExecutionException;
    void updateSavingsDormancyStatus() throws JobExecutionException;
}
