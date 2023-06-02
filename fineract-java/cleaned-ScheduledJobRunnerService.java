
package org.apache.fineract.scheduledjobs.service;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
public interface ScheduledJobRunnerService {
    void applyAnnualFeeForSavings();
    void applyDueChargesForSavings() throws JobExecutionException;
    void updateNPA();
    void updateMaturityDetailsOfDepositAccounts();
    void generateRDSchedule();
    void postDividends() throws JobExecutionException;
    void updateTrialBalanceDetails() throws JobExecutionException;
    void executeMissMatchedJobs() throws JobExecutionException;
}
