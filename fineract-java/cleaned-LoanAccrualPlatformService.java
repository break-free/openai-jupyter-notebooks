
package org.apache.fineract.portfolio.loanaccount.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.exception.MultiException;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.portfolio.loanaccount.data.LoanScheduleAccrualData;
public interface LoanAccrualPlatformService {
    void addPeriodicAccruals(LocalDate tilldate) throws MultiException;
    void addPeriodicAccruals(LocalDate tilldate, Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas) throws MultiException;
    void addAccrualAccounting() throws JobExecutionException;
    void addPeriodicAccruals() throws JobExecutionException;
    void addPeriodicAccrualsForLoansWithIncomePostedAsTransactions() throws JobExecutionException;
}
