
package org.apache.fineract.portfolio.loanaccount.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.portfolio.loanaccount.data.LoanScheduleAccrualData;
public interface LoanAccrualWritePlatformService {
    void addAccrualAccounting(Long loanId, Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas) throws Exception;
    void addPeriodicAccruals(LocalDate tilldate, Long loanId, Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas)
            throws Exception;
    void addIncomeAndAccrualTransactions(Long loanId) throws Exception;
}
